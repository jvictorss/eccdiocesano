package br.com.verbum.eccdiocesano.rest.mappers.interfaces;

import br.com.verbum.eccdiocesano.domain.entities.Conjuge;
import br.com.verbum.eccdiocesano.rest.dtos.ConjugeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConjugeMapper {
    Conjuge mapConjugeToEntity(ConjugeDto conjugeDto);
}
