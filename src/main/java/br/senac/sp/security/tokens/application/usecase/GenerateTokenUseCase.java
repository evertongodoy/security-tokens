package br.senac.sp.security.tokens.application.usecase;

import br.senac.sp.security.tokens.domain.entity.Token;

public interface GenerateTokenUseCase {

    Token execute(String subject);

}
