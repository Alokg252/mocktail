package com.flarecon.mocktail.controller;

import com.flarecon.mocktail.Constants;
import com.flarecon.mocktail.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ApiKeyService apiKeyService;

    @PostMapping("/api-keys")
    public Map<String, Object> issue(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_KEY_LABEL) String label, String name) {
        ApiKeyService.IssuedKey issued = apiKeyService.issue(name, label);
        return Map.of(
                "key", issued.rawKey(),
                "label", label,
                "expiresAt", issued.record().getExpiresAt().toString(),
                "note", "Store this key now; the server only keeps a hash."
        );
    }

    @PostMapping("/ping")
    public Map<String, Object> ping() {
        return Map.of("ok", true, "now", LocalDateTime.now().toString());
    }
}
