package br.senac.sp.security.tokens.infrastructure.implementation;

import br.senac.sp.security.tokens.domain.entity.Token;
import br.senac.sp.security.tokens.domain.service.JwtService;
import br.senac.sp.security.tokens.infrastructure.config.JwtProperties;
import br.senac.sp.security.tokens.repository.UsuariosRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;
    private final UsuariosRepository usuariosRepository;
    private static final String SCOPES = "scopes";

    private static final Logger logger = LogManager.getLogger(JwtServiceImpl.class);

    public JwtServiceImpl(JwtProperties jwtProperties,
                          UsuariosRepository usuariosRepository) {
        this.jwtProperties = jwtProperties;
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public Token generateToken(String subject) {
        logger.info("[JwtServiceImpl]-[generateToken] - Criando token para o subject {}", subject);
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
        logger.info("[JwtServiceImpl]-[validateToken] - Validando token recebido {}", token);
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            logger.info("[JwtServiceImpl]-[validateToken] - Erro ao validar token {}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> getScopes(String token) {
        logger.info("[JwtServiceImpl]-[getScopes] - Recuperando scopes para o token recebido");
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Object scopes = claims.get(SCOPES);
        if(Objects.isNull(scopes)){
            logger.info("[JwtServiceImpl]-[getScopes] - Nao foram localizados scopes no token");
            return List.of();
        }
        return (List<String>) scopes;
    }

    private Key getSigningKey() {
        logger.info("[JwtServiceImpl]-[getSigningKey] - Decodificando a chave secreta armazenada em Base64 no Application.yml em um array de bytes[]");
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        logger.info("[JwtServiceImpl]-[getSigningKey] - Converte os bytes da chave secreta (keyBytes) em um objeto Key adequado para assinatura JWT com algoritmo HMAC-SHA (HS256, HS384, HS512)");
        return Keys.hmacShaKeyFor(keyBytes);
    }

}