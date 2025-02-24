package br.senac.sp.security.tokens.domain.entity;

public class Token {

    private final String value;

    public Token(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}