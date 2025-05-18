package ve312.com.filoseries.service;

import ve312.com.filoseries.domain.Episodio;
import ve312.com.filoseries.domain.Serie;

import java.util.List;
import java.util.Optional;

public interface EpisodioService {

    List<Episodio> listarEpisodios();
    Optional<Episodio> buscarPorId(Long id);
    Episodio guardar(Episodio episodio);
    void eliminar(Long id);
    List<Episodio> buscarPorSerie(Serie serie);

    // Métodos para internacionalización
    String getTituloLocalizado(Episodio episodio);
    String getDescripcionLocalizada(Episodio episodio);
}
