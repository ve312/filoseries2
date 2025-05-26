package ve312.com.filoseries.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ve312.com.filoseries.domain.Usuario;
import ve312.com.filoseries.service.UsuarioService;
import ve312.com.filoseries.util.MessageUtil;

@Controller
@Tag(name = "Autenticación", description = "Controlador para la gestión de autenticación y registro de usuarios")
public class AuthController {
    private final UsuarioService usuarioService;
    //private final AuthenticationManager authenticationManager;
    private final MessageUtil messageUtil;

    @Autowired
    public AuthController(UsuarioService usuarioService,MessageUtil messageUtil) {
        this.usuarioService = usuarioService;
        this.messageUtil = messageUtil;
    }

    @GetMapping("/login")
    @Operation(summary = "Mostrar formulario de login",
            description = "Muestra la página de inicio de sesión con mensajes de error si corresponde")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Formulario de login mostrado correctamente")
    })
    public String mostrarFormularioLogin(@RequestParam(value = "error", required = false) String error,
                                         @RequestParam(value = "logout", required = false) String logout,
                                         Authentication authentication,
                                         Model model) {

        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null) {
            if ("inactive".equals(error)) {
                model.addAttribute("error", messageUtil.getMessage("login.error.usuario.inactivo"));
            } else {
                model.addAttribute("error", messageUtil.getMessage("login.error.credenciales"));
            }
        }

        if (logout != null) {
            model.addAttribute("mensaje", messageUtil.getMessage("logout.success"));
        }

        return "auth/login";
    }


    @GetMapping("/registro")
    @Operation(summary = "Mostrar formulario de registro",
            description = "Muestra la página de registro de nuevos usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Formulario de registro mostrado correctamente")
    })
    public String mostrarFormularioRegistro(Authentication authentication,Model model) {

        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping("/registro")
    @Operation(summary = "Procesar registro de usuario",
            description = "Valida y procesa el registro de un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Usuario registrado correctamente, redirige a la página principal"),
            @ApiResponse(responseCode = "200", description = "Error en el registro, muestra nuevamente el formulario con errores")
    })
    public String procesarRegistro(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        // Validar si el usuario ya existe
        if (usuarioService.existeUsername(usuario.getUsername())) {
            result.rejectValue("username", "error.username", "El nombre de usuario ya está en uso");
        }

        // Validar si el email ya existe
        if (usuarioService.existeEmail(usuario.getEmail())) {
            result.rejectValue("email", "error.email", "El email ya está registrado");
        }

        // Si hay errores de validación, volver al formulario
        if (result.hasErrors()) {
            return "auth/registro";
        }

        try {
            // Guardar usuario
            usuarioService.guardar(usuario);

            redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso");
            return "redirect:/";
        } catch (Exception e) {
            // Manejar cualquier error de registro
            result.reject("error.registro", "Error al registrar el usuario");
            return "auth/registro";
        }
    }

    @GetMapping("/acceso-denegado")
    @Operation(summary = "Mostrar página de acceso denegado",
            description = "Muestra la página de error cuando un usuario intenta acceder a un recurso sin permisos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de acceso denegado mostrada correctamente")
    })
    public String accesoDenegado() {
        return "error/acceso-denegado";
    }

}
