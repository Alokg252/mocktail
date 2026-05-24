package com.flarecon.mocktail.security;

import com.flarecon.mocktail.Constants;
import com.flarecon.mocktail.model.ApiKey;
import com.flarecon.mocktail.service.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    public static final String HEADER = Constants.TOKEN_HEADER;

    private final ApiKeyService apiKeyService;
    private final RateLimiter rateLimiter;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String key = request.getHeader(HEADER);
        if (key == null || key.isBlank()) {
            key = stripBearer(request.getHeader(HttpHeaders.AUTHORIZATION));
        }

        Optional<ApiKey> resolved = apiKeyService.resolve(key);
        if (resolved.isEmpty()) {
            writeJson(response, HttpServletResponse.SC_UNAUTHORIZED,
                    "{\"error\":\"invalid or missing API key\"}");
            return;
        }

        ApiKey apiKey = resolved.get();

        final int limit = switch (apiKey.getLabel().trim().toLowerCase()) {
            case Constants.ADMIN_LABEL -> Constants.RATE_LIMIT_ADMIN_PER_HOUR;
            case Constants.TEST_LABEL -> Constants.RATE_LIMIT_TEST_PER_HOUR;
            default -> Constants.RATE_LIMIT_DEFAULT_PER_HOUR;
        };

        RateLimiter.Result result = rateLimiter.tryAcquire(apiKey.getKeyHash(), limit);
        response.setHeader("X-RateLimit-Limit", String.valueOf(result.limit()));
        response.setHeader("X-RateLimit-Remaining", String.valueOf(result.remaining()));

        if (!result.allowed()) {
            response.setHeader("Retry-After", String.valueOf(result.retryAfterSeconds()));
            writeJson(response, 429,
                    "{\"error\":\"rate limit exceeded\",\"limit\":" + result.limit()
                            + ",\"retryAfterSeconds\":" + result.retryAfterSeconds() + "}");
            return;
        }
        chain.doFilter(request, response);
    }

    private static void writeJson(HttpServletResponse response, int status, String body) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(body);
    }

    private static String stripBearer(String header) {
        if (header == null) return null;
        String prefix = Constants.TOKEN_PREFIX;
        return header.regionMatches(true, 0, prefix, 0, prefix.length())
                ? header.substring(prefix.length()).trim()
                : header.trim();
    }
}
