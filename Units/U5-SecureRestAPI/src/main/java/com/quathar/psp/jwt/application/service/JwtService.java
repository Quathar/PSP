package com.quathar.psp.jwt.application.service;

import com.quathar.psp.jwt.data.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <h1>JWT (Jason Web Token) Service</h1>
 *
 * @since 2023-01-XX
 * @version 1.0
 * @author jagui, Q
 */
@Service
public class JwtService {

    // <<-FIELDS->>
    private final byte[] _secretKeyByteArray;

    // <<-CONSTRUCTOR->>
    JwtService(
            @Value("${jwt.secret-key}")
            String secretKey
    ) {
        _secretKeyByteArray = secretKey.getBytes();
    }

    // <<-METHODS->>
    public String createJwt(User user) {
        Date now = new Date();
        // 10 minutes
        long expirationTime = 60 * 10 * 1000;
        return Jwts.builder()
                   .setSubject(user.getUsername())
                   .setIssuedAt(now)
                   .setExpiration(new Date(now.getTime() + expirationTime))
                   .signWith(Keys.hmacShaKeyFor(_secretKeyByteArray))
                   .compact();
    }

    public User extractUserFromJwt(String jwt) {
        Claims claims = extractClaims(jwt);
        return new User(claims.getSubject(), "");
    }

    private Claims extractClaims(String jwt) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(Keys.hmacShaKeyFor(_secretKeyByteArray))
                       .build()
                       .parseClaimsJws(jwt)
                       .getBody();
        } catch (Exception ex) {
            throw new JwtParseException(ex);
        }
    }

    static class JwtParseException extends RuntimeException {

        public JwtParseException(Exception ex) {
            super(ex);
        }

    }

}
