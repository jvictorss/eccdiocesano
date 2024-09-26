package br.com.verbum.eccdiocesano.domain.repository;

import br.com.verbum.eccdiocesano.domain.entities.Diocese;
import br.com.verbum.eccdiocesano.domain.reuse.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DioceseRepository extends BaseRepository<Diocese> {

    Optional<Diocese> findByNomeDioceseAndCidadeDiocese(String nomeDiocese, String cidadeDiocese);

}
