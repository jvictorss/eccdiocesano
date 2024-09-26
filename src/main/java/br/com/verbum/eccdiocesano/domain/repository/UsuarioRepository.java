package br.com.verbum.eccdiocesano.domain.repository;

import br.com.verbum.eccdiocesano.domain.entities.Usuario;
import br.com.verbum.eccdiocesano.domain.reuse.BaseRepository;

import java.util.Optional;

public interface UsuarioRepository extends BaseRepository<Usuario> {

    Optional<Usuario> findByEmail(String email);
}
