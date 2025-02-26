package br.senac.sp.security.tokens.infrastructure.config;

import br.senac.sp.security.tokens.interceptor.ScopeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ScopeInterceptor scopeInterceptor;

    public WebConfig(ScopeInterceptor scopeInterceptor) {
        this.scopeInterceptor = scopeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(scopeInterceptor);//.order(1);//.addPathPatterns("/**");
    }

}