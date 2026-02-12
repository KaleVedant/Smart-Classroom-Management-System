package com.smartclassroom.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

    // ✅ must be at least 256 bits for HS256
    private static final String SECRET_KEY =
            "smartclassroom_secret_key_1234567890123456";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60; // 1 hour

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String subject) {

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername (String token){
        return extractAllClaims(token).getSubject();
    }
    public boolean isTokenValid(String token){
        try {
            extractAllClaims(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(String subject, String role) {

        return Jwts.builder()
                .setSubject(subject)
                .claim("role", role)   // ✅ add role
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }


}
