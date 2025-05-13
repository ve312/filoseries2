package ve312.com.filoseries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ve312.com.filoseries.domain.Serie;

import java.util.List;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
    List<Serie> findByTituloEsContainingIgnoreCase(String titulo);
    List<Serie> findByTituloEnContainingIgnoreCase(String titulo);
    List<Serie> findByTituloDeContainingIgnoreCase(String titulo);
}
