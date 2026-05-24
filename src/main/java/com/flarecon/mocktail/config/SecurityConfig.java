package com.flarecon.mocktail.config;

import com.flarecon.mocktail.Constants;
import com.flarecon.mocktail.security.ApiKeyFilter;
import com.flarecon.mocktail.security.RateLimiter;
import com.flarecon.mocktail.service.ApiKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> apiKeyFilterRegistration(ApiKeyService apiKeyService,
                                                                         RateLimiter rateLimiter) {
        FilterRegistrationBean<ApiKeyFilter> reg = new FilterRegistrationBean<>(new ApiKeyFilter(apiKeyService, rateLimiter));
        reg.addUrlPatterns("/mcp/*", "/admin/*");
        reg.setOrder(1);
        return reg;
    }

    @Bean
    public CommandLineRunner bootstrapApiKey(ApiKeyService apiKeyService) {
        return args -> {
            if (apiKeyService.count() == 0) {
                ApiKeyService.IssuedKey issued = apiKeyService.issue("admin", Constants.DEFAULT_ADMIN_LABEL);
                log.warn(Constants.PIPE_LINE);
                log.warn(" No API keys found. Bootstrap key issued (valid 1 month):");
                log.warn("   {}", issued.rawKey());
                log.warn(" Save this now — it cannot be retrieved later.");
                log.warn(" Use header: X-API-KEY: <key>   on /mcp/** and /admin/**");
                log.warn(Constants.PIPE_LINE);
            }
        };
    }
}
