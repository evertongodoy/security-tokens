package br.senac.sp.security.tokens.application.usecase.impl;

import br.senac.sp.security.tokens.application.usecase.ValidateTokenUseCase;
import br.senac.sp.security.tokens.domain.service.JwtService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ValidateTokenUseCaseImpl implements ValidateTokenUseCase {

    private final JwtService jwtService;
    private static final Logger logger = LogManager.getLogger(ValidateTokenUseCaseImpl.class);

    public ValidateTokenUseCaseImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public boolean execute(String token) {
        logger.info("[ValidateTokenUseCaseImpl]-[execute] - Iniciando usecase para o token {}", token);
        return jwtService.validateToken(token);
    }

}
