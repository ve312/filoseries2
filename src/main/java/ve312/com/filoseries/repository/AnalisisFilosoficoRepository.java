package ve312.com.filoseries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ve312.com.filoseries.domain.AnalisisFilosofico;
import ve312.com.filoseries.domain.CategoriaFilosofica;
import ve312.com.filoseries.domain.Episodio;
import ve312.com.filoseries.domain.Filosofo;

import java.util.List;

@Repository
public interface AnalisisFilosoficoRepository extends JpaRepository<AnalisisFilosofico, Long> {
    List<AnalisisFilosofico> findByEpisodio(Episodio episodio);
    List<AnalisisFilosofico> findByFilosofo(Filosofo filosofo);
    List<AnalisisFilosofico> findByCategoria(CategoriaFilosofica categoria);
    List<AnalisisFilosofico> findTop10ByOrderByFechaPublicacionDesc();
}
