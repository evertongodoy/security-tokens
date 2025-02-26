package br.senac.sp.security.tokens.infrastructure.config;

import br.senac.sp.security.tokens.interceptor.ScopeInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ScopeInterceptor scopeInterceptor;

    private static final Logger logger = LogManager.getLogger(WebConfig.class);

    public WebConfig(ScopeInterceptor scopeInterceptor) {
        this.scopeInterceptor = scopeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("[WebConfig]-[addInterceptors] - Recebe um InterceptorRegistry, registro de interceptores, pode ser um ou mais. Adiciona o scopeInterceptor ao registro de interceptores do Spring Boot");
        registry.addInterceptor(scopeInterceptor);
        // .addPathPatterns("/**"); // padrões de URL explícitos.
        // .addPathPatterns("/recuperar/todos") // Somente este endpoint será interceptado
        // .order(1); // Define a prioridade para evitar conflitos com outros interceptors
    }

}