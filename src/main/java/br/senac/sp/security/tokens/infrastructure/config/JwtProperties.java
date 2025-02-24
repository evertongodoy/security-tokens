package br.senac.sp.security.tokens.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secretKey;
    private long expirationMs;

    public String getSecretKey() {
        return secretKey;
    }

    public JwtProperties setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    public JwtProperties setExpirationMs(long expirationMs) {
        this.expirationMs = expirationMs;
        return this;
    }

}