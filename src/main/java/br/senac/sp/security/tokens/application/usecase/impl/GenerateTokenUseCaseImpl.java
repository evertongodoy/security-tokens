package br.senac.sp.security.tokens.application.usecase.impl;

import br.senac.sp.security.tokens.application.usecase.GenerateTokenUseCase;
import br.senac.sp.security.tokens.domain.entity.Token;
import br.senac.sp.security.tokens.domain.service.JwtService;
import org.springframework.stereotype.Service;

@Service
public class GenerateTokenUseCaseImpl implements GenerateTokenUseCase {

    private final JwtService jwtService;

    public GenerateTokenUseCaseImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public Token execute(String subject) {
        return jwtService.generateToken(subject);
    }

}
