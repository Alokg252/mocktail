package com.flarecon.mocktail.security;

import com.flarecon.mocktail.Constants;
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

@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    public static final String HEADER = Constants.TOKEN_HEADER;

    private final ApiKeyService apiKeyService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String key = request.getHeader(HEADER);
        if (key == null || key.isBlank()) {
            key = stripBearer(request.getHeader(HttpHeaders.AUTHORIZATION));
        }

        if (!apiKeyService.isValid(key)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"error\":\"invalid or missing API key\"}");
            return;
        }
        chain.doFilter(request, response);
    }

    private static String stripBearer(String header) {
        if (header == null) return null;
        String prefix = Constants.TOKEN_PREFIX;
        return header.regionMatches(true, 0, prefix, 0, prefix.length())
                ? header.substring(prefix.length()).trim()
                : header.trim();
    }
}
