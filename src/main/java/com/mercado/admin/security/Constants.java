package com.mercado.admin.security;

import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class Constants {
    // Spring Security
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    // JWT - ✅ Hardcodeado para desarrollo académico (sin @Value)
    public static final String SUPER_SECRET_TEXT = "X7k$9Lm!qR2vZp@8#dF4tYw0BnC3uHjK5sE&aM6QxLrPzT";
    public static final long TOKEN_VALIDITY_SECONDS = 600_000; // 10 minutos

    public static Key getSigningKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}