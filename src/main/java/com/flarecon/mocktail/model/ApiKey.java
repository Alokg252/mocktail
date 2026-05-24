package com.flarecon.mocktail.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "api_keys")
public class ApiKey implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, length = 128)
    String keyHash;

    @Column(nullable = false, unique = true, length = 200)
    String name;

    @Column(length = 64)
    String label;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDateTime expiresAt;

    @Column(nullable = false)
    boolean revoked = false;

    public ApiKey(String keyHash, String name, String label, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.keyHash = keyHash;
        this.label = label;
        this.name = name;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public boolean isUsable(LocalDateTime now) {
        return !revoked && now.isBefore(expiresAt);
    }
}
