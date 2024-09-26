package br.com.verbum.eccdiocesano.rest.mappers;

import br.com.verbum.eccdiocesano.domain.entities.Diocese;
import br.com.verbum.eccdiocesano.domain.entities.Paroquia;
import br.com.verbum.eccdiocesano.rest.dtos.DioceseDto;
import br.com.verbum.eccdiocesano.rest.dtos.ParoquiaDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ParoquiaMapper {

    public Paroquia mapToEntity(ParoquiaDto paroquiaDto) {
        return Paroquia.builder()
                .nome(paroquiaDto.getNome())
                .cidade(paroquiaDto.getCidade())
                .estado(paroquiaDto.getEstado())
                .idSetor(paroquiaDto.getIdSetor())
                .idDiocese(paroquiaDto.getIdDiocese())
                .isActive(paroquiaDto.getIsActive())
                .build();
    }

    public ParoquiaDto mapToDto(Paroquia paroquia) {
        return ParoquiaDto.builder()
                .id(paroquia.getId())
                .nome(paroquia.getNome())
                .cidade(paroquia.getCidade())
                .estado(paroquia.getEstado())
                .idSetor(paroquia.getIdSetor())
                .idDiocese(paroquia.getIdDiocese())
                .isActive(paroquia.getIsActive())
                .build();
    }

    public List<ParoquiaDto> mapToDto(List<Paroquia> paroquias) {
        return paroquias.stream()
                .map(this::mapToDto)
                .toList();
    }
}
