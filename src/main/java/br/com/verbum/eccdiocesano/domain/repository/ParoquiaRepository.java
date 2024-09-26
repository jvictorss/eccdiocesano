package br.com.verbum.eccdiocesano.domain.repository;

import br.com.verbum.eccdiocesano.domain.entities.Casal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasalRepository extends JpaRepository<Casal, Long> {
}
