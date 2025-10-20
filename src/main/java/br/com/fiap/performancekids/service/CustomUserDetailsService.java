package br.com.fiap.performancekids.service;

import br.com.fiap.performancekids.entity.Funcionario;
import br.com.fiap.performancekids.repository.FuncionarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final FuncionarioRepository funcionarioRepository;

    public CustomUserDetailsService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscando o usuário no banco de dados
        Funcionario funcionario = funcionarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Retorna o UserDetails com a senha (sem criptografia por enquanto)
        return User.builder()
                .username(funcionario.getEmail())  // E-mail como nome de usuário
                .password("{noop}" + funcionario.getSenha())  // Senha não criptografada (para teste)
                .roles(funcionario.getCargo())
                .build();
    }

    // Método para salvar o usuário (sem criptografia da senha)
    public void salvarUsuario(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);  // Senha será salva como está, sem criptografia
    }
}
