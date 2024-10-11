package br.com.verbum.eccdiocesano.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class TelasController {

    @GetMapping("/v1/diocese/form")
    public String showCreateDioceseForm() {
        return "create_diocese";
    }

    @GetMapping("/v1/diocese/view")
    public String showSeeDioceseForm() {
        return "diocese_view";
    }

    @GetMapping("/v1/setor/form")
    public String showCreateSetorForm() {
        return "create_setor";
    }

    @GetMapping("/v1/setor/view")
    public String showSeeSetorForm() {
        return "setor_view";
    }

    @GetMapping("/v1/paroquia/form")
    public String showCreateParoquiaForm() {
        return "create_paroquia";
    }

    @GetMapping("/v1/paroquia/view")
    public String showSeeParoquiaForm() {
        return "paroquia_view";
    }

    @GetMapping("/v1/casal/form")
    public String showCreateCasalForm() {
        return "create_casal";
    }

    @GetMapping("/v1/usuario/form")
    public String showCreateUsuarioForm() {return "create_usuario";}

    @GetMapping("/v1/usuario/view")
    public String showSeeUsuarioForm() {
        return "usuario_view";
    }

    @GetMapping("/v1/casal/view")
    public String showSeeCasalForm() {
        return "casal_summary_view";
    }

    @GetMapping("/v1/casal/view/{id}")
    public String showViewCasalForm(@PathVariable("id") UUID id) {
        return "casal_visualizacao";
    }

    @GetMapping("/v1/login")
    public String showLogin() {
        return "form_login";
    }

    @GetMapping("/v1/home")
    public String showHome() {
        return "home";
    }

    @GetMapping("/v1/navbar")
    public String showNavBar() {
        return "novo-navbar";
    }

    @GetMapping("/v1/js")
    public String showJs() {
        return "/scripts/functions";
    }

    @GetMapping("/v1/footer")
    public String showFooter() {
        return "footer";
    }


//    @GetMapping("/v1/usuario/criar")
//    public String showUsuarioCreate() {
//        return "create_usuario";
//    }
//
//    @GetMapping("/v1/usuario/editar")
//    public String showUsuarioUpdate() {
//        return "update_usuario";
//    }
//
//    @GetMapping("/v1/usuario/visualizar")
//    public String showUsuarios() {
//        return "usuario_view";
//    }
}
