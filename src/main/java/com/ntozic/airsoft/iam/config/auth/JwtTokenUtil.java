package com.ntozic.airsoft.iam.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public final class JwtTokenUtil {
    @Value("${authentication.jwt.secret}")
    private String jwtSecret;
    
    @Value("${authentication.jwt.expiration}")
    private int jwtExpirationMinutes;

    public Key getKey() {
        return Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(final UserDetails userDetails) {
        var now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(getExpiration())
                .setIssuedAt(getIssuedAt())
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getTokenFromHeader(String authHeader) {
        return authHeader.split(" ", 2)[1].trim();
    }

    public String refreshToken(final String token) {
        var now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(getEmail(token))
                .setExpiration(getExpiration())
                .setIssuedAt(getIssuedAt())
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims getClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpiration(final String token) {
        return getClaims(token).getExpiration();
    }

    public boolean isTokenValid(String token) {
        var claims = getClaims(token);

        return getClaims(token).getSubject() != null
                && !claims.getSubject().isBlank()
                && claims.getExpiration() != null
                && claims.getExpiration().after(new Date());
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    private Date getExpiration() {
        return Date.from(LocalDateTime.now().plusMinutes(jwtExpirationMinutes).toInstant(ZoneOffset.UTC));
    }

    private Date getIssuedAt() {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }
}
