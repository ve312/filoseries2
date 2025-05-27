package ve312.com.filoseries.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import ve312.com.filoseries.domain.AnalisisFilosofico;
import ve312.com.filoseries.domain.Episodio;
import ve312.com.filoseries.domain.Serie;
import ve312.com.filoseries.service.AnalisisFilosoficoService;
import ve312.com.filoseries.service.EpisodioService;
import ve312.com.filoseries.service.SerieService;

import java.util.*;

@Controller
@RequestMapping("/series")
@Tag(name = "Series", description = "Controlador para la gestión y visualización de series")
public class SerieController {

    private final SerieService serieService;
    private final EpisodioService episodioService;
    private final AnalisisFilosoficoService analisisFilosoficoService;

    @Autowired
    public SerieController(SerieService serieService, EpisodioService episodioService, AnalisisFilosoficoService analisisFilosoficoService) {
        this.serieService = serieService;
        this.episodioService = episodioService;
        this.analisisFilosoficoService = analisisFilosoficoService;
    }
    @GetMapping
    @Operation(summary = "Listar todas las series",
            description = "Muestra la lista completa de series disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de series mostrada correctamente")
    })
    public String listarSeries(Model model) {
        model.addAttribute("series", serieService.listarSeries());
        model.addAttribute("serieSer", serieService);
        return "series/lista";
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ver detalle de una serie",
            description = "Muestra la página de detalle de una serie con sus temporadas, episodios y análisis filosóficos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie encontrada y mostrada correctamente"),
            @ApiResponse(responseCode = "302", description = "Serie no encontrada, redirige a la lista de series")
    })
    public String verDetalleSerie(@PathVariable Long id, Model model) {
        Optional<Serie> optionalSerie = serieService.buscarPorId(id);

        if (optionalSerie.isPresent()) {
            Serie serie = optionalSerie.get();
            List<Episodio> episodios = episodioService.buscarPorSerie(serie);

            // Preprocesar datos que antes usaban streams en la vista
            List<Integer> temporadas = extraerTemporadas(serie.getEpisodios());
            Map<Integer, List<Episodio>> episodiosPorTemporada = agruparEpisodiosPorTemporada(serie.getEpisodios());
            List<AnalisisFilosofico> todosAnalisis = extraerTodosAnalisis(serie.getEpisodios());

            model.addAttribute("serie", serie);
            model.addAttribute("episodios", episodios);
            model.addAttribute("temporadas", temporadas);
            model.addAttribute("episodiosPorTemporada", episodiosPorTemporada);
            model.addAttribute("todosAnalisis", todosAnalisis);
            model.addAttribute("hayAnalisis", !todosAnalisis.isEmpty());

            // Servicios para localización
            model.addAttribute("serieSer", serieService);
            model.addAttribute("episodioSer", episodioService);
            model.addAttribute("analisisSer", analisisFilosoficoService);

            return "detalle/serie";
        } else {
            return "redirect:/series";
        }
    }

    //Los metodos auxilaires no necesitan doc ya que son privados
    // Métodos auxiliares para preprocesar datos que antes se hacían con streams en las vistas
    private List<Integer> extraerTemporadas(List<Episodio> episodios) {
        Set<Integer> temporadasSet = new HashSet<>();
        for (Episodio episodio : episodios) {
            temporadasSet.add(episodio.getNumeroTemporada());
        }
        List<Integer> temporadas = new ArrayList<>(temporadasSet);
        Collections.sort(temporadas);
        return temporadas;
    }

    private Map<Integer, List<Episodio>> agruparEpisodiosPorTemporada(List<Episodio> episodios) {
        Map<Integer, List<Episodio>> resultado = new HashMap<>();

        for (Episodio episodio : episodios) {
            int temporada = episodio.getNumeroTemporada();
            resultado.computeIfAbsent(temporada, k -> new ArrayList<>()).add(episodio);
        }

        // Ordenar los episodios por número dentro de cada temporada
        for (List<Episodio> listaEpisodios : resultado.values()) {
            listaEpisodios.sort(Comparator.comparingInt(Episodio::getNumeroEpisodio));
        }

        return resultado;
    }

    private List<AnalisisFilosofico> extraerTodosAnalisis(List<Episodio> episodios) {
        List<AnalisisFilosofico> resultado = new ArrayList<>();

        for (Episodio episodio : episodios) {
            if (episodio.getAnalisis() != null) {
                resultado.addAll(episodio.getAnalisis());
            }
        }

        return resultado;
    }

}
