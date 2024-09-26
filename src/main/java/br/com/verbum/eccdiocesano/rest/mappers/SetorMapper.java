package br.com.verbum.eccdiocesano.rest.mappers;

import br.com.verbum.eccdiocesano.domain.entities.Diocese;
import br.com.verbum.eccdiocesano.rest.dtos.DioceseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@AllArgsConstructor
public class DioceseMapper {

    public Diocese mapToEntity(DioceseDto dioceseDto) {
        return Diocese.builder()
                .nomeDiocese(dioceseDto.getNome())
                .cidadeDiocese(dioceseDto.getCidade())
                .estadoDiocese(dioceseDto.getEstado())
                .isActive(dioceseDto.getIsActive())
                .createdByUser("SYSTEM")
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();
    }

    public DioceseDto mapToDto(Diocese diocese) {
        return DioceseDto.builder()
                .id(diocese.getId())
                .nome(diocese.getNomeDiocese())
                .cidade(diocese.getCidadeDiocese())
                .estado(diocese.getEstadoDiocese())
                .isActive(diocese.getIsActive())
                .build();
    }
}
