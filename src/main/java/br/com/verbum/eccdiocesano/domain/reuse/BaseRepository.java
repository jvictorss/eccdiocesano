package br.com.verbum.eccdiocesano.domain.reuse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, UUID> {

    List<T> findByIsActive(Boolean isActive);

    List<T> findAllById(UUID id);

    Optional<T> findById(UUID id);

    List<T> findAllByIsActive(boolean isActive);
}
