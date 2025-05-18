package ve312.com.filoseries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve312.com.filoseries.domain.Episodio;
import ve312.com.filoseries.domain.Serie;
import ve312.com.filoseries.repository.EpisodioRepository;
import ve312.com.filoseries.util.LocalizationUtil;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodioServiceImpl implements EpisodioService {

    private final EpisodioRepository episodioRepository;
    private final LocalizationUtil localizationUtil;

    @Autowired
    public EpisodioServiceImpl(EpisodioRepository episodioRepository, LocalizationUtil localizationUtil) {
        this.episodioRepository = episodioRepository;
        this.localizationUtil = localizationUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Episodio> listarEpisodios() {
        return episodioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Episodio> buscarPorId(Long id) {
        return episodioRepository.findById(id);
    }

    @Override
    @Transactional
    public Episodio guardar(Episodio episodio) {
        return episodioRepository.save(episodio);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        episodioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Episodio> buscarPorSerie(Serie serie) {
        return episodioRepository.findBySerie(serie);
    }

    @Override
    public String getTituloLocalizado(Episodio episodio) {
        return switch (localizationUtil.getCurrentLanguage()) {
            case "en" -> episodio.getTituloEn();
            case "de" -> episodio.getTituloDe();
            default -> episodio.getTituloEs();
        };
    }

    @Override
    public String getDescripcionLocalizada(Episodio episodio) {
        return switch (localizationUtil.getCurrentLanguage()) {
            case "en" -> episodio.getDescripcionEn();
            case "de" -> episodio.getDescripcionDe();
            default -> episodio.getDescripcionEs();
        };
    }
}
