package br.com.fiap.performancekids.controller;

import br.com.fiap.performancekids.entity.Funcionario;
import br.com.fiap.performancekids.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private FuncionarioService funcionarioService;

    // Página de login
    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

    // Página de cadastro
    @GetMapping("/auth/cadastro")
    public String cadastroForm(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "auth/cadastro";
    }

    // Processa o cadastro (POST)
    @PostMapping("/auth/cadastro")
    public String cadastrar(@Valid @ModelAttribute("funcionario") Funcionario funcionario,
                            BindingResult result,
                            Model model) {

        // validação do formulário
        if (result.hasErrors()) {
            model.addAttribute("erro", "Preencha corretamente os campos.");
            return "auth/cadastro";
        }

        // verifica se já existe e-mail
        if (funcionarioService.buscarPorEmail(funcionario.getEmail()).isPresent()) {
            model.addAttribute("erro", "Já existe um usuário cadastrado com este e-mail.");
            return "auth/cadastro";
        }

        // salva (sem criptografia, conforme combinado)
        funcionarioService.salvar(funcionario);

        // redireciona para login com flag de sucesso
        return "redirect:/auth/login?sucesso";
    }

    // Logout apenas redireciona (Spring Security já controla sessão)
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/auth/login?logout";
    }
}
