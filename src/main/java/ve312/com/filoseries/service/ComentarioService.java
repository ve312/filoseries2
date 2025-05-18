package ve312.com.filoseries.service;

import ve312.com.filoseries.domain.AnalisisFilosofico;
import ve312.com.filoseries.domain.Comentario;
import ve312.com.filoseries.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface ComentarioService {

    List<Comentario> listarComentarios();
    Optional<Comentario> buscarPorId(Long id);
    Comentario guardar(Comentario comentario);
    void eliminar(Long id);
    List<Comentario> buscarPorUsuario(Usuario usuario);
    List<Comentario> buscarPorAnalisisOrdenadosPorFecha(AnalisisFilosofico analisis);
}
