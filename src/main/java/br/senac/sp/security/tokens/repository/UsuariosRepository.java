package br.senac.sp.security.tokens.repository;

import java.util.List;
import java.util.Map;

public interface UsuariosRepository {

    Map<String, List<String>> findScopesByUsuario(final String subject);

}
