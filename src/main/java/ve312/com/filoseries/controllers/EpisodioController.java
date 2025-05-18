package ve312.com.filoseries.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ve312.com.filoseries.domain.Episodio;
import ve312.com.filoseries.domain.Serie;
import ve312.com.filoseries.service.AnalisisFilosoficoService;
import ve312.com.filoseries.service.EpisodioService;
import ve312.com.filoseries.service.SerieService;

import java.util.Optional;

@Controller
@RequestMapping("/episodios")
@Tag(name = "Episodios", description = "Controlador para la gestión y visualización de episodios de series")
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
    @Operation(summary = "Ver detalle de un episodio",
            description = "Muestra la página de detalle de un episodio con su información, análisis filosóficos y navegación entre episodios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Episodio encontrado y mostrado correctamente"),
            @ApiResponse(responseCode = "302", description = "Episodio no encontrado, redirige a la página de series")
    })
    public String verDetalleEpisodio(@PathVariable Long id, Model model) {
        Optional<Episodio> episodioOpt = episodioService.buscarPorId(id);

        if (episodioOpt.isPresent()) {
            Episodio episodio = episodioOpt.get();
            Serie serie = episodio.getSerie();

            // Preprocesar datos para la navegación entre episodios
            Episodio episodioAnterior = encontrarEpisodioAnterior(serie, episodio);
            Episodio episodioSiguiente = encontrarEpisodioSiguiente(serie, episodio);

            model.addAttribute("episodio", episodio);
            model.addAttribute("serie", serie);
            model.addAttribute("analisis", analisisService.buscarPorEpisodio(episodio));
            model.addAttribute("episodioAnterior", episodioAnterior);
            model.addAttribute("episodioSiguiente", episodioSiguiente);
            model.addAttribute("tieneEpisodioAnterior", episodioAnterior != null);
            model.addAttribute("tieneEpisodioSiguiente", episodioSiguiente != null);

            // Servicios para localización
            model.addAttribute("episodioSer", episodioService);
            model.addAttribute("serieSer", serieService);
            model.addAttribute("analisisSer", analisisService);

            return "detalle/episodio";
        } else {
            return "redirect:/series";
        }
    }

    // Los métodos auxiliares no necesitan documentación de OpenAPI ya que son privados
    // Métodos auxiliares para preprocesar datos que antes se hacían con streams en las vistas
    private Episodio encontrarEpisodioAnterior(Serie serie, Episodio actual) {
        if (actual.getNumeroEpisodio() <= 1) {
            return null;
        }

        // Buscar episodio anterior en la misma temporada
        for (Episodio e : serie.getEpisodios()) {
            if (e.getNumeroTemporada() == actual.getNumeroTemporada() &&
                    e.getNumeroEpisodio() == actual.getNumeroEpisodio() - 1) {
                return e;
            }
        }

        return null;
    }

    private Episodio encontrarEpisodioSiguiente(Serie serie, Episodio actual) {
        // Buscar episodio siguiente en la misma temporada
        for (Episodio e : serie.getEpisodios()) {
            if (e.getNumeroTemporada() == actual.getNumeroTemporada() &&
                    e.getNumeroEpisodio() == actual.getNumeroEpisodio() + 1) {
                return e;
            }
        }

        return null;
    }

}
