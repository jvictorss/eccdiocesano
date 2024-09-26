package br.com.verbum.eccdiocesano.domain.services;

import br.com.verbum.eccdiocesano.domain.entities.Usuario;
import br.com.verbum.eccdiocesano.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public Usuario loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> user = repository.findByEmail(email);

        user.orElseThrow(() -> new UsernameNotFoundException(email + " não encontrado."));

        return user.get();
    }
}
