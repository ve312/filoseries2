package ve312.com.filoseries.service;

import ve312.com.filoseries.domain.Filosofo;

import java.util.List;
import java.util.Optional;

public interface FilosofoService {
    List<Filosofo> listarFilosofos();
    Optional<Filosofo> buscarPorId(Long id);
    Filosofo guardar(Filosofo filosofo);
    void eliminar(Long id);
    List<Filosofo> buscarPorNombre(String nombre);
}
