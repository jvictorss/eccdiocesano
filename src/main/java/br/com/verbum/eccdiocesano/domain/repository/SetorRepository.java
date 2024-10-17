package br.com.verbum.eccdiocesano.domain.repository;

import br.com.verbum.eccdiocesano.domain.entities.Setor;
import br.com.verbum.eccdiocesano.domain.reuse.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SetorRepository extends BaseRepository<Setor> {

    @Query(value = "SELECT COUNT(1) FROM setor WHERE id_diocese = ?1", nativeQuery = true)
    int findSetorByIdDiocese(UUID dioceseId);
}
