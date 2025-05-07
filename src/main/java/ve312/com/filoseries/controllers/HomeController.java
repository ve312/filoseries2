package ve312.com.filoseries.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ve312.com.filoseries.service.AnalisisFilosoficoService;
import ve312.com.filoseries.service.SerieService;

@Controller
public class HomeController {
    private final SerieService serieService;
    private final AnalisisFilosoficoService analisisService;

    @Autowired
    public HomeController(SerieService serieService, AnalisisFilosoficoService analisisService) {
        this.serieService = serieService;
        this.analisisService = analisisService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Obtener series para mostrar en la página principal
        model.addAttribute("series", serieService.listarSeries());

        // Obtener los últimos análisis para mostrar en la página principal
        model.addAttribute("ultimosAnalisis", analisisService.buscarUltimosAnalisis(5));

        return "home";
    }

    @GetMapping("/cambiar-idioma")
    public String cambiarIdioma() {
        // El cambio de idioma lo maneja Spring automáticamente con LocaleChangeInterceptor
        return "redirect:/";
    }
}
