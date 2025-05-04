package com.expense_tracker.expense_tracker.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private final static long expirationMillis = (long)24 * 60 * 60 * 1000; // 1 day

    public SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    // Generate JWT with userId in 'sub', emailId as a custom claim, and roles
    public String generateToken(String email, long userId, List<String> roles) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))  // userId as 'sub'
                .claim("email", email)  // email as a custom claim
                .claim("roles", roles)  // roles as a custom claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract roles from the JWT
    public List<String> extractRoles(String token) {
        return getClaims(token).get("roles", List.class);
    }

    // Extract email from the JWT
    public String extractEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    // Extract userId (sub) from the JWT
    public String extractUserId(String token) {
        return getClaims(token).getSubject();  // 'sub' will be userId
    }

    // Validate the JWT token
    public boolean validateToken(String token) {
        try {
            getClaims(token);  // Will throw an exception if token is invalid
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
