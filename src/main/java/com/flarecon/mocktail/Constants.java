package com.flarecon.mocktail;

public interface Constants {
    String SUCCESS_MESSAGE = "cheers!!!";
    String TOKEN_HEADER = "X-API-KEY";
    String TOKEN_PREFIX = "Bearer";
    String PIPE_LINE = "=========================================================";
    String DEFAULT_KEY_LABEL = "issued";
    String DEFAULT_ADMIN_LABEL = "bootstrap";
    String TEST_LABEL = "test";
    String ADMIN_LABEL = "admin";
    int RATE_LIMIT_ADMIN_PER_HOUR = 100;
    int RATE_LIMIT_DEFAULT_PER_HOUR = 30;
    int RATE_LIMIT_TEST_PER_HOUR = 10;
}
