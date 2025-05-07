package ve312.com.filoseries.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ve312.com.filoseries.domain.Episodio;
import ve312.com.filoseries.service.AnalisisFilosoficoService;
import ve312.com.filoseries.service.EpisodioService;
import ve312.com.filoseries.service.SerieService;

import java.util.Optional;

@Controller
@RequestMapping("/episodios")
public class EpisodioController {

    private final EpisodioService episodioService;
    private final SerieService serieService;
    private final AnalisisFilosoficoService analisisService;

    @Autowired
    public EpisodioController(EpisodioService episodioService,
                              SerieService serieService,
                              AnalisisFilosoficoService analisisService) {
        this.episodioService = episodioService;
        this.serieService = serieService;
        this.analisisService = analisisService;
    }

    @GetMapping("/{id}")
    public String verDetalleEpisodio(@PathVariable Long id, Model model) {
        Optional<Episodio> episodio = episodioService.buscarPorId(id);

        if (episodio.isPresent()) {
            model.addAttribute("episodio", episodio.get());
            model.addAttribute("serie", episodio.get().getSerie());
            model.addAttribute("analisis", analisisService.buscarPorEpisodio(episodio.get()));

            return "episodios/detalle";
        } else {
            return "redirect:/series";
        }
    }
}
