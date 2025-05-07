package ve312.com.filoseries.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class AuthController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final MessageUtil messageUtil;

    @Autowired
    public AuthController(UsuarioService usuarioService, AuthenticationManager authenticationManager, MessageUtil messageUtil) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.messageUtil = messageUtil;
    }


    @GetMapping("/login")
    public String mostrarFormularioLogin(@RequestParam(value = "error", required = false) String error,
                                         @RequestParam(value = "logout", required = false) String logout,
                                         Model model) {
        if (error != null) {
            model.addAttribute("error", messageUtil.getMessage("login.error"));
        }

        if (logout != null) {
            model.addAttribute("mensaje", messageUtil.getMessage("logout.success"));
        }

        return "auth/login";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        // Validar si el usuario ya existe
        if (usuarioService.existeUsername(usuario.getUsername())) {
            result.rejectValue("username", "error.username", messageUtil.getMessage("registro.error.username.existente"));
        }

        // Validar si el email ya existe
        if (usuarioService.existeEmail(usuario.getEmail())) {
            result.rejectValue("email", "error.email", messageUtil.getMessage("registro.error.email.existente"));
        }

        if (result.hasErrors()) {
            return "auth/registro";
        }

        usuarioService.guardar(usuario);

        // Autenticamos al usuario después del registro
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuario.getUsername(),
                        usuario.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        redirectAttributes.addFlashAttribute("mensaje", messageUtil.getMessage("registro.exito"));
        return "redirect:/";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "error/acceso-denegado";
    }
}
