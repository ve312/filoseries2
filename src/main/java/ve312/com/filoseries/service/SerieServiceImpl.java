package ve312.com.filoseries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve312.com.filoseries.domain.Serie;
import ve312.com.filoseries.repository.SerieRepository;
import ve312.com.filoseries.util.LocalizationUtil;

import java.util.List;
import java.util.Optional;

@Service
public class SerieServiceImpl implements SerieService {

    private final SerieRepository serieRepository;
    private final LocalizationUtil localizationUtil;

    @Autowired
    public SerieServiceImpl(SerieRepository serieRepository, LocalizationUtil localizationUtil) {
        this.serieRepository = serieRepository;
        this.localizationUtil = localizationUtil;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Serie> listarSeries() {
        return serieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Serie> buscarPorId(Long id) {
        return serieRepository.findById(id);
    }

    @Override
    @Transactional
    public Serie guardar(Serie serie) {
        return serieRepository.save(serie);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        serieRepository.deleteById(id);
    }

    //usando todos los metodos

    @Override
    @Transactional(readOnly = true)
    public List<Serie> buscarPorTitulo(String titulo) {

        String language = LocaleContextHolder.getLocale().getLanguage();

        return switch (language) {
            case "en" -> serieRepository.findByTituloEnContainingIgnoreCase(titulo);
            case "de" -> serieRepository.findByTituloDeContainingIgnoreCase(titulo);
            default -> serieRepository.findByTituloEsContainingIgnoreCase(titulo);
        };
    }

    //sintetizando con el metodo creado

    @Override
    public String getTituloLocalizado(Serie serie) {
        return switch (localizationUtil.getCurrentLanguage()) {
            case "en" -> serie.getTituloEn();
            case "de" -> serie.getTituloDe();
            default -> serie.getTituloEs();
        };
    }

    @Override
    public String getDescripcionLocalizada(Serie serie) {
        return switch (localizationUtil.getCurrentLanguage()) {
            case "en" -> serie.getDescripcionEn();
            case "de" -> serie.getDescripcionDe();
            default -> serie.getDescripcionEs();
        };
    }
}
