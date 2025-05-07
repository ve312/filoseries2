package ve312.com.filoseries.service;

import ve312.com.filoseries.domain.CategoriaFilosofica;

import java.util.List;
import java.util.Optional;

public interface CategoriaFilosoficaService {

    List<CategoriaFilosofica> listarCategorias();
    Optional<CategoriaFilosofica> buscarPorId(Long id);
    CategoriaFilosofica guardar(CategoriaFilosofica categoria);
    void eliminar(Long id);

    // Métodos para internacionalización
    String getNombreLocalizado(CategoriaFilosofica categoria);
}
