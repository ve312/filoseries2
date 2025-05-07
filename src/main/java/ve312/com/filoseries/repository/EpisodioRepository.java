package ve312.com.filoseries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ve312.com.filoseries.domain.Episodio;
import ve312.com.filoseries.domain.Serie;

import java.util.List;

@Repository
public interface EpisodioRepository extends JpaRepository<Episodio, Long> {
    List<Episodio> findBySerie(Serie serie);
    List<Episodio> findBySerieAndNumeroTemporada(Serie serie, int temporada);
    List<Episodio> findBySerieOrderByNumeroTemporadaAscNumeroEpisodioAsc(Serie serie);
}
