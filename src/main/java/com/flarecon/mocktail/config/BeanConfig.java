package com.flarecon.mocktail.config;

import com.flarecon.mocktail.tool.UserTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ToolCallbackProvider userTools(UserTool userTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(userTool)
                .build();
    }
}
