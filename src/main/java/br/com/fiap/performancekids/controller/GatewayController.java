package br.com.fiap.performancekids.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GatewayController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index")
    public String indexHome() {
        return "index";
    }

    @GetMapping("/go/funcionarios")
    public String goFuncionarios() {
        if (hasRole("ROLE_ADMIN")) {
            return "redirect:/funcionarios";
        }
        return "erros/negado-admin";
    }

    @GetMapping("/go/categorias")
    public String goCategorias() {
        if (hasRole("ROLE_ADMIN") || hasRole("ROLE_OPERADOR")) {
            return "redirect:/categorias";
        }
        return "erros/negado-operador";
    }

    @GetMapping("/go/brinquedos")
    public String goBrinquedos() {
        if (hasRole("ROLE_ADMIN") || hasRole("ROLE_OPERADOR")) {
            return "redirect:/brinquedos";
        }
        return "erros/negado-operador";
    }

    private boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) return false;
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role));
    }
}
