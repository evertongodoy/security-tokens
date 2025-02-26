package br.senac.sp.security.tokens.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})  // No caso ElementType.METHOD, significa que a anotacao so pode ser aplicada a metodos.
@Retention(RetentionPolicy.RUNTIME)  // Significa que a anotação será mantida durante a execucao do programa, permitindo sua verificacao pelo interceptor.
public @interface EscopoNecessario { // Define uma nova anotacao personalizada chamada @EscopoNecessario.
    String[] value();  // Define um parâmetro obrigatório, que recebe os escopos necessários para acessar o metodo atraves da anotacao.
}