package com.flarecon.mocktail.service;

import com.flarecon.mocktail.model.ApiKey;
import com.flarecon.mocktail.repository.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiKeyService {

    public static final int TTL_MONTHS = 1;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final ApiKeyRepository repository;

    public record IssuedKey(String rawKey, ApiKey record) {}

    @Transactional
    public IssuedKey issue(String name, String label) {
        String raw = generateRawKey();
        LocalDateTime now = LocalDateTime.now();
        ApiKey saved = repository.save(new ApiKey(
                hash(raw),
                label,
                name,
                now,
                now.plusMonths(TTL_MONTHS)
        ));
        return new IssuedKey(raw, saved);
    }

    @Transactional(readOnly = true)
    public Optional<ApiKey> resolve(String rawKey) {
        if (rawKey == null || rawKey.isBlank()) return Optional.empty();
        return repository.findByKeyHash(hash(rawKey))
                .filter(k -> k.isUsable(LocalDateTime.now()));
    }

    @Transactional(readOnly = true)
    public boolean isValid(String rawKey) {
        if (rawKey == null || rawKey.isBlank()) return false;
        return repository.findByKeyHash(hash(rawKey))
                .map(k -> k.isUsable(LocalDateTime.now()))
                .orElse(false);
    }

    public long count() {
        return repository.count();
    }

    private static String generateRawKey() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String hash(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }
}
