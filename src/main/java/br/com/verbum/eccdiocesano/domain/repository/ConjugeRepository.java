package br.com.verbum.eccdiocesano.domain.repository;

import br.com.verbum.eccdiocesano.domain.entities.Casal;
import br.com.verbum.eccdiocesano.domain.entities.Conjuge;
import br.com.verbum.eccdiocesano.domain.reuse.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConjugeRepository extends BaseRepository<Conjuge> {

    // procurar todos os casais da paróquia que estão ativos

    // query escrita para procurar casais da paróquia entre ativos e inativos;

    // query escrita para procurar casais que fizeram determinada etapa

    // query escrita para procurar casais que foram transferidos;
}
