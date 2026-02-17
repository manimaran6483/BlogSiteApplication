package com.api.auth.service.security;

import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret:}")
    private String secret;

    @Value("${jwt.expiration-ms:86400000}")
    private long expirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        if (secret != null && StringUtils.isNotBlank(secret)) {
            try {
                byte[] keyBytes = Decoders.BASE64.decode(secret.trim());
                this.key = Keys.hmacShaKeyFor(keyBytes);
                logger.info("Initialized JWT signing key from configured secret.");
                return;
            } catch (IllegalArgumentException | WeakKeyException ex) {
                logger.warn("Configured jwt.secret is invalid or too weak; falling back to a generated secure key. Replace jwt.secret with a 256-bit Base64 value for persistent tokens.", ex);
            }
        } else {
            logger.warn("No jwt.secret configured; generating a secure ephemeral signing key. Tokens will not survive application restarts.");
        }

        // Fallback: generate a secure random key suitable for HS256
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(String subject) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
