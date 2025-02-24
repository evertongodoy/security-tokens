package br.senac.sp.security.tokens.repository.impl;

import br.senac.sp.security.tokens.repository.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuariosRepositoryImpl implements UsuariosRepository {

    @Override
    public Map<String, List<String>> findScopesByUsuario(String subject) {
        Map<String, List<String>> claims = new HashMap<>();
        if("senac".equalsIgnoreCase(subject)){
            claims.put("scopes", List.of("listar-filmes", "editar-filmes", "deletar-filmes"));
        }
        return claims;
    }

}