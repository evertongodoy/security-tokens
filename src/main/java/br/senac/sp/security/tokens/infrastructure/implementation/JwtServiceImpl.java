package br.senac.sp.security.tokens.infrastructure.implementation;

import br.senac.sp.security.tokens.domain.entity.Token;
import br.senac.sp.security.tokens.domain.service.JwtService;
import br.senac.sp.security.tokens.infrastructure.config.JwtProperties;
import br.senac.sp.security.tokens.repository.UsuariosRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;
    private final UsuariosRepository usuariosRepository;

    public JwtServiceImpl(JwtProperties jwtProperties,
                          UsuariosRepository usuariosRepository) {
        this.jwtProperties = jwtProperties;
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public Token generateToken(String subject) {
        Map<String, List<String>> claims = usuariosRepository.findScopesByUsuario(subject);
        String jwt = Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMs()))
                .signWith(getSigningKey())
                .compact();
        return new Token(jwt);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> getScopes(String key, String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Object scopes = claims.get(key);
        if(Objects.isNull(scopes)){
            return List.of();
        }
        return (List<String>) scopes;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}