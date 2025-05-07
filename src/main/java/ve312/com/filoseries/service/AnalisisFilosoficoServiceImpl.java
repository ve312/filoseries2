package ve312.com.filoseries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve312.com.filoseries.domain.AnalisisFilosofico;
import ve312.com.filoseries.domain.CategoriaFilosofica;
import ve312.com.filoseries.domain.Episodio;
import ve312.com.filoseries.domain.Filosofo;
import ve312.com.filoseries.repository.AnalisisFilosoficoRepository;
import ve312.com.filoseries.util.LocalizationUtil;

import java.util.List;
import java.util.Optional;

@Service
public class AnalisisFilosoficoServiceImpl implements AnalisisFilosoficoService {

    private final AnalisisFilosoficoRepository analisisRepository;
    private final LocalizationUtil localizationUtil;

    @Autowired
    public AnalisisFilosoficoServiceImpl(AnalisisFilosoficoRepository analisisRepository,
                                         LocalizationUtil localizationUtil) {
        this.analisisRepository = analisisRepository;
        this.localizationUtil = localizationUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnalisisFilosofico> listarAnalisis() {
        return analisisRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalisisFilosofico> buscarPorId(Long id) {
        return analisisRepository.findById(id);
    }

    @Override
    @Transactional
    public AnalisisFilosofico guardar(AnalisisFilosofico analisis) {
        return analisisRepository.save(analisis);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        analisisRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnalisisFilosofico> buscarPorEpisodio(Episodio episodio) {
        return analisisRepository.findByEpisodio(episodio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnalisisFilosofico> buscarPorFilosofo(Filosofo filosofo) {
        return analisisRepository.findByFilosofo(filosofo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnalisisFilosofico> buscarPorCategoria(CategoriaFilosofica categoria) {
        return analisisRepository.findByCategoria(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnalisisFilosofico> buscarUltimosAnalisis(int cantidad) {
        return analisisRepository.findTop10ByOrderByFechaPublicacionDesc();
    }

    @Override
    public String getTituloLocalizado(AnalisisFilosofico analisis) {
        return switch (localizationUtil.getCurrentLanguage()) {
            case "en" -> analisis.getTitulo_en();
            case "de" -> analisis.getTitulo_de();
            default -> analisis.getTitulo_es();
        };
    }

    @Override
    public String getContenidoLocalizado(AnalisisFilosofico analisis) {
        return switch (localizationUtil.getCurrentLanguage()) {
            case "en" -> analisis.getContenido_en();
            case "de" -> analisis.getContenido_de();
            default -> analisis.getContenido_es();
        };
    }
}
