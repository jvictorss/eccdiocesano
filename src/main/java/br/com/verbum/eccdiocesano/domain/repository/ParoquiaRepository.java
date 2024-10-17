package br.com.verbum.eccdiocesano.domain.repository;

import br.com.verbum.eccdiocesano.domain.entities.Paroquia;
import br.com.verbum.eccdiocesano.domain.reuse.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParoquiaRepository extends BaseRepository<Paroquia> {

    Optional<Paroquia> findByNomeAndCidade(String nomeParoquia, String cidadeParoquia);

    @Query(value = "SELECT COUNT(1) FROM paroquia WHERE id_setor = ?1", nativeQuery = true)
    int findParoquiaByIdSetor(UUID idSetor);
}
