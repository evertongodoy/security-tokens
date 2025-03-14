package br.senac.sp.security.tokens.repository.impl;

import br.senac.sp.security.tokens.repository.UsuariosRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuariosRepositoryImpl implements UsuariosRepository {

    private static final Logger logger = LogManager.getLogger(UsuariosRepositoryImpl.class);

    private static final String CAMINHO_ARQUIVO = System.getProperty("user.dir") + "/usuarios.txt";

    @Override
    public Map<String, List<String>> findScopesByUsuario(String subject) {
        logger.info("[UsuariosRepositoryImpl]-[findScopesByUsuario] - Simulando a busca do usuario {} em um banco de dados", subject);

        String caminhoArquivo = "usuarios.txt"; // Altere para o caminho correto do arquivo

        Map<String, List<String>> claims = new HashMap<>();
        Map<String, List<String>> usuariosScopes = lerArquivoUsuarios();

        if (usuariosScopes.containsKey(subject.toLowerCase())) {
            logger.info("[UsuariosRepositoryImpl]-[findScopesByUsuario] - Recuperando os Scopes para o usuário {}", subject);
            claims.put("scopes", usuariosScopes.get(subject.toLowerCase())); // Adiciona os escopos do usuário encontrado
        }
        return claims;
    }

    private static Map<String, List<String>> lerArquivoUsuarios() {
        Map<String, List<String>> usuarioScopes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" ", 2); // Divide a linha em duas partes (usuário e escopos)
                if (partes.length == 2) {
                    String usuario = partes[0].trim();
                    List<String> scopes = Arrays.asList(partes[1].split(",\\s*")); // Quebra os escopos por vírgula e remove espaços extras
                    usuarioScopes.put(usuario, scopes);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return usuarioScopes;
    }

}