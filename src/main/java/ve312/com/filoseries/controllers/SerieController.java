package ve312.com.filoseries.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import ve312.com.filoseries.domain.Serie;
import ve312.com.filoseries.service.EpisodioService;
import ve312.com.filoseries.service.SerieService;

import java.util.Optional;

@Controller
@RequestMapping("/series")
public class SerieController {

    private final SerieService serieService;
    private final EpisodioService episodioService;

    @Autowired
    public SerieController(SerieService serieService, EpisodioService episodioService) {
        this.serieService = serieService;
        this.episodioService = episodioService;
    }

    @GetMapping
    public String listarSeries(Model model) {
        model.addAttribute("series", serieService.listarSeries());
        return "series/lista";
    }

    @GetMapping("/{id}")
    public String verDetalleSerie(@PathVariable Long id, Model model) {
        Optional<Serie> serie = serieService.buscarPorId(id);

        if (serie.isPresent()) {
            model.addAttribute("serie", serie.get());
            // Agrupar episodios por temporada
            model.addAttribute("episodios", episodioService.buscarPorSerie(serie.get()));

            return "series/detalle";
        } else {
            return "redirect:/series";
        }
    }

    @GetMapping("/buscar")
    public String buscarSeries(@RequestParam String termino, Model model) {
        model.addAttribute("series", serieService.buscarPorTitulo(termino));
        model.addAttribute("termino", termino);
        return "series/lista";
    }
}
