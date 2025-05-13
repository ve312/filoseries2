package ve312.com.filoseries.service;

import org.springframework.stereotype.Service;
import ve312.com.filoseries.domain.AnalisisFilosofico;
import ve312.com.filoseries.domain.CategoriaFilosofica;
import ve312.com.filoseries.domain.Episodio;
import ve312.com.filoseries.domain.Filosofo;

import java.util.List;
import java.util.Optional;


public interface AnalisisFilosoficoService {

    List<AnalisisFilosofico> listarAnalisis();
    Optional<AnalisisFilosofico> buscarPorId(Long id);
    AnalisisFilosofico guardar(AnalisisFilosofico analisis);
    void eliminar(Long id);
    List<AnalisisFilosofico> buscarPorEpisodio(Episodio episodio);
    List<AnalisisFilosofico> buscarPorFilosofo(Filosofo filosofo);
    List<AnalisisFilosofico> buscarPorCategoria(CategoriaFilosofica categoria);
    List<AnalisisFilosofico> buscarUltimosAnalisis(int cantidad);

    // Métodos para internacionalización
    String getTituloLocalizado(AnalisisFilosofico analisis);
    String getContenidoLocalizado(AnalisisFilosofico analisis);
}
