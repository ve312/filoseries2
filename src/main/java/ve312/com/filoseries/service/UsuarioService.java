package ve312.com.filoseries.service;

import ve312.com.filoseries.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listarUsuarios();
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorUsername(String username);
    Optional<Usuario> buscarPorEmail(String email);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    boolean existeUsername(String username);
    boolean existeEmail(String email);
}
