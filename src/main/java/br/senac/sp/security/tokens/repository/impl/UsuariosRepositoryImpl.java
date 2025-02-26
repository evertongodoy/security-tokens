package br.senac.sp.security.tokens.repository.impl;

import br.senac.sp.security.tokens.repository.UsuariosRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuariosRepositoryImpl implements UsuariosRepository {

    private static final Logger logger = LogManager.getLogger(UsuariosRepositoryImpl.class);

    @Override
    public Map<String, List<String>> findScopesByUsuario(String subject) {
        logger.info("[UsuariosRepositoryImpl]-[findScopesByUsuario] - Simulando a busca do usuario {} em um banco de dados", subject);
        Map<String, List<String>> claims = new HashMap<>();
        if("senac".equalsIgnoreCase(subject)){
            logger.info("[UsuariosRepositoryImpl]-[findScopesByUsuario] - Recuperando os Scopes para o usuario");
            claims.put("scopes", List.of("listar-filmes", "editar-filmes", "deletar-filmes"));
        }
        return claims;
    }

}