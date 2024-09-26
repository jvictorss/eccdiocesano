package br.com.verbum.eccdiocesano.domain.repository;

import br.com.verbum.eccdiocesano.domain.entities.Paroquia;
import br.com.verbum.eccdiocesano.domain.reuse.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParoquiaRepository extends BaseRepository<Paroquia> {

    Optional<Paroquia> findByNomeAndCidade(String nomeParoquia, String cidadeParoquia);
}
