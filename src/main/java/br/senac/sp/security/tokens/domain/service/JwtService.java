package br.senac.sp.security.tokens.domain.service;

import br.senac.sp.security.tokens.domain.entity.Token;

import java.util.List;

public interface JwtService {

    Token generateToken(String subject);
    boolean validateToken(String token);
    List<String> getScopes(String key, String token);

}
