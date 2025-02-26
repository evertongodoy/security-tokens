package br.senac.sp.security.tokens.interceptor;

import br.senac.sp.security.tokens.annotation.EscopoNecessario;
import br.senac.sp.security.tokens.domain.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Component
public class ScopeInterceptor implements HandlerInterceptor {

    private final JwtService jwtService; // Classe que valida o JWT

    public ScopeInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            System.out.println("Método interceptado: " + method.getName());

            // Se o metodo não estiver anotado com @EscopoNecessario, permite a requisição sem validar token
            if (!method.isAnnotationPresent(EscopoNecessario.class)) {
                return true;
            }

            if (method.isAnnotationPresent(EscopoNecessario.class)) {
                System.out.println("Anotação @RequireScope encontrada!");
                EscopoNecessario requireScope = method.getAnnotation(EscopoNecessario.class);
                String token = extractToken(request);

                if (token == null || !jwtService.validateToken(token)) {
                    System.out.println("Token inválido ou ausente.");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou ausente.");
                    return false;
                }

                List<String> userScopes = jwtService.getScopes(token);
                List<String> requiredScopes = Arrays.asList(requireScope.value());

                System.out.println("Escopos do usuário: " + userScopes);

                if (!userScopes.containsAll(requiredScopes)) {
                    System.out.println("Permissão negada. Escopos insuficientes.");
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Permissão negada. Escopos insuficientes.");
                    return false;
                }
            }
        }
        return true;
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

}
