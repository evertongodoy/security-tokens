package br.senac.sp.security.tokens.application.usecase.impl;

import br.senac.sp.security.tokens.application.usecase.ValidateTokenUseCase;
import br.senac.sp.security.tokens.domain.service.JwtService;
import org.springframework.stereotype.Service;

@Service
public class ValidateTokenUseCaseImpl implements ValidateTokenUseCase {

    private final JwtService jwtService;

    public ValidateTokenUseCaseImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public boolean execute(String token) {
        return jwtService.validateToken(token);
    }

}
