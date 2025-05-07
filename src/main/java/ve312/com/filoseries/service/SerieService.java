package ve312.com.filoseries.service;

import ve312.com.filoseries.domain.Serie;

import java.util.List;
import java.util.Optional;

public interface SerieService {
    List<Serie> listarSeries();
    Optional<Serie> buscarPorId(Long id);
    Serie guardar(Serie serie);
    void eliminar(Long id);
    List<Serie> buscarPorTitulo(String titulo);

    // Métodos para internacionalización
    String getTituloLocalizado(Serie serie);
    String getDescripcionLocalizada(Serie serie);
}
