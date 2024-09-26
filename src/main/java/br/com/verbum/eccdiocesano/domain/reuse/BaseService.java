package br.com.verbum.eccdiocesano.domain.reuse;

import org.springframework.http.ResponseEntity;

public abstract class BaseService<T extends BaseEntity> {

    protected abstract BaseRepository<T> getRepository();

//    public ResponseEntity<Iterable<T>> findAll(boolean isActive) {
//        var enitity = getRepository().findAllByIsActive(isActive);
//
//        return ResponseEntity.ok(dioceseMapper.mapToDto(dioceses));
//    }
}
