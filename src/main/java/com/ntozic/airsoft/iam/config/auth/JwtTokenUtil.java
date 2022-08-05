package com.ntozic.airsoft.iam.config.auth;

import com.ntozic.airsoft.iam.dao.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public final class JwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${authentication.jwt.secret}")
    private String jwtSecret;
    
    @Value("${authentication.jwt.expiration}")
    private int jwtExpirationMinutes;

    public String getTokenFromHeader(String authHeader) {
        return authHeader.split(" ", 2)[1].trim();
    }

    public String generateToken(final UserDto user) {
        var now = System.currentTimeMillis();
        return Jwts.builder()
                .setId(user.getReference())
                .setSubject("USER")
                .setExpiration(createExpiration())
                .setIssuedAt(createIssuedAt())
                .signWith(createKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String refreshToken(final String token) {
        return Jwts.builder()
                .setId(getReference(token))
                .setSubject("USER")
                .setExpiration(createExpiration())
                .setIssuedAt(createIssuedAt())
                .signWith(createKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims getClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(createKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpiration(final String token) {
        return getClaims(token).getExpiration();
    }

    public boolean isValid(String token) {
        try {
            var claims = getClaims(token);
            return getClaims(token).getSubject() != null
                    && !claims.getSubject().isBlank()
                    && claims.getExpiration() != null
                    && claims.getExpiration().after(new Date());
        } catch (InvalidKeyException | SignatureException e) {
            logger.warn("Incoming request with invalid key.", e);
            return false;
        }
    }

    public String getReference(String token) {
        return getClaims(token).getId();
    }

    public String getAuthority(String token) {
        return getClaims(token).getSubject();
    }

    private Key createKey() {
        return Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    private Date createExpiration() {
        return Date.from(LocalDateTime.now().plusMinutes(jwtExpirationMinutes).toInstant(ZoneOffset.UTC));
    }

    private Date createIssuedAt() {
        return Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }
}
