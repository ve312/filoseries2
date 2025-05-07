package ve312.com.filoseries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ve312.com.filoseries.domain.AnalisisFilosofico;
import ve312.com.filoseries.domain.Comentario;
import ve312.com.filoseries.domain.Usuario;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByAnalisis(AnalisisFilosofico analisis);
    List<Comentario> findByUsuario(Usuario usuario);
    List<Comentario> findByAnalisisOrderByFechaCreacionDesc(AnalisisFilosofico analisis);
}
