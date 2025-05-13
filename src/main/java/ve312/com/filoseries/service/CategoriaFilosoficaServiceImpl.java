package ve312.com.filoseries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve312.com.filoseries.domain.CategoriaFilosofica;
import ve312.com.filoseries.repository.CategoriaFilosoficaRepository;
import ve312.com.filoseries.util.LocalizationUtil;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaFilosoficaServiceImpl implements CategoriaFilosoficaService {

     final CategoriaFilosoficaRepository categoriaRepository;
    private final LocalizationUtil localizationUtil;

    @Autowired
    public CategoriaFilosoficaServiceImpl(CategoriaFilosoficaRepository categoriaRepository,
                                          LocalizationUtil localizationUtil) {
        this.categoriaRepository = categoriaRepository;
        this.localizationUtil = localizationUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaFilosofica> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaFilosofica> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    @Transactional
    public CategoriaFilosofica guardar(CategoriaFilosofica categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public String getNombreLocalizado(CategoriaFilosofica categoria) {
        return switch (localizationUtil.getCurrentLanguage()) {
            case "en" -> categoria.getNombreEn();
            case "de" -> categoria.getNombreDe();
            default -> categoria.getNombreEs();
        };
    }
}
