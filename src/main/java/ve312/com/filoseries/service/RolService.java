package ve312.com.filoseries.service;

import ve312.com.filoseries.domain.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> listarRoles();
    Optional<Rol> buscarPorId(Long id);
    Optional<Rol> buscarPorNombre(String nombre);
    Rol guardar(Rol rol);
    void eliminar(Long id);
}
