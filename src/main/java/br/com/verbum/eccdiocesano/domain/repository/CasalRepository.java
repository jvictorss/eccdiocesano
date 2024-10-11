package br.com.verbum.eccdiocesano.domain.repository;

import br.com.verbum.eccdiocesano.domain.entities.Casal;
import br.com.verbum.eccdiocesano.domain.reuse.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CasalRepository extends BaseRepository<Casal> {

    // procurar todos os casais da paróquia que estão ativos

    // query escrita para procurar casais da paróquia entre ativos e inativos;

    // query escrita para procurar casais que fizeram determinada etapa

    // query escrita para procurar casais que foram transferidos;

    Optional<Casal> findCasalByEleCpfAndElaCpf(String eleCpf, String elaCpf);

    Optional<Casal> findCasalByParoquiaAtualId(UUID idParoquia);
}
