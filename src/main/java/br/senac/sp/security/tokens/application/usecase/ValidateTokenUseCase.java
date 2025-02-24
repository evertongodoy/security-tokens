package br.senac.sp.security.tokens.application.usecase;

public interface ValidateTokenUseCase {

    boolean execute(String token);

}