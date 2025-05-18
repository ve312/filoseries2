package ve312.com.filoseries.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ve312.com.filoseries.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listarUsuarios();
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorUsername(String username);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    boolean existeUsername(String username);
    boolean existeEmail(String email);
    Page<Usuario> listarUsuariosNoAdmin(Pageable pageable);
    Page<Usuario> buscarUsuarioPorIdNoAdmin(Long id, Pageable pageable);
    void cambiarEstadoUsuario(Long id) throws Exception;
    void eliminarUsuario(Long id) throws Exception;
}
