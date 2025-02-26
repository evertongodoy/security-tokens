package br.senac.sp.security.tokens.interceptor;

import br.senac.sp.security.tokens.annotation.EscopoNecessario;
import br.senac.sp.security.tokens.domain.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Component
public class ScopeInterceptor implements HandlerInterceptor {

    private final JwtService jwtService; // Classe que valida o JWT

    private static final Logger logger = LogManager.getLogger(ScopeInterceptor.class);

    public ScopeInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("[ScopeInterceptor]-[preHandle] - Verificando se o objeto que esta manipulando a requisição HTTP pode ser um metodo dentro de um Controller (HandlerMethod) ou algo diferente, como um recurso estatico");
        if (handler instanceof HandlerMethod handlerMethod) { // introduzido no Java 16
            Method method = handlerMethod.getMethod();
            logger.info("[ScopeInterceptor]-[preHandle] - Metodo interceptado {} ", method.getName());

            if (method.isAnnotationPresent(EscopoNecessario.class)) {
                logger.info("[ScopeInterceptor]-[preHandle] - Metodo utiliza anotacao @EscopoNecessario");
                EscopoNecessario requireScope = method.getAnnotation(EscopoNecessario.class);
                String token = extractToken(request);

                if (token == null || !jwtService.validateToken(token)) { // Validando o token
                    logger.info("[ScopeInterceptor]-[preHandle] - [401] - Token invalido ou ausente.");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou ausente.");
                    return false;
                }

                List<String> userScopes = jwtService.getScopes(token);
                List<String> requiredScopes = Arrays.asList(requireScope.value());

                logger.info("[ScopeInterceptor]-[preHandle] - Scopes do usuario {}", userScopes);
                logger.info("[ScopeInterceptor]-[preHandle] - Scopes necessarios {}", requiredScopes);

                if (!userScopes.containsAll(requiredScopes)) {
                    logger.info("[ScopeInterceptor]-[preHandle] - [403] - Permissao negada. Escopos insuficientes");
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Permissão negada. Escopos insuficientes.");
                    return false;
                }
            } else {
                // Se o metodo não estiver anotado com @EscopoNecessario, permite a requisição sem validar token
                logger.info("[ScopeInterceptor]-[preHandle] - Metodo nao utiliza anotacao @EscopoNecessario");
                return true;
            }
        }
        logger.info("[ScopeInterceptor]-[preHandle] - Nao e metodo dentro de um Controller (HandlerMethod) ou algo diferente");
        return true;
    }

    private String extractToken(HttpServletRequest request) {
        logger.info("[ScopeInterceptor]-[extractToken] - Extraindo token");
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        logger.info("[ScopeInterceptor]-[extractToken] - Token nao localizado");
        return null;
    }

}