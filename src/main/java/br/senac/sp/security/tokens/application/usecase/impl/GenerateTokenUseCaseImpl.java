package br.senac.sp.security.tokens.application.usecase.impl;

import br.senac.sp.security.tokens.application.usecase.GenerateTokenUseCase;
import br.senac.sp.security.tokens.domain.entity.Token;
import br.senac.sp.security.tokens.domain.service.JwtService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GenerateTokenUseCaseImpl implements GenerateTokenUseCase {

    private final JwtService jwtService;
    private static final Logger logger = LogManager.getLogger(GenerateTokenUseCaseImpl.class);

    public GenerateTokenUseCaseImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public Token execute(String subject) {
        logger.info("[GenerateTokenUseCaseImpl]-[execute] - Iniciando usecase para o subject {}", subject);
        return jwtService.generateToken(subject);
    }

}
