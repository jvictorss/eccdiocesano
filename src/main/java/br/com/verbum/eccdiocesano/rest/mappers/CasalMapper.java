package br.com.verbum.eccdiocesano.rest.mappers;

import br.com.verbum.eccdiocesano.domain.entities.Diocese;
import br.com.verbum.eccdiocesano.domain.entities.Setor;
import br.com.verbum.eccdiocesano.rest.dtos.DioceseDto;
import br.com.verbum.eccdiocesano.rest.dtos.SetorDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class SetorMapper {

    public Setor mapToEntity(SetorDto setorDto) {
        return Setor.builder()
                .nomeSetor(setorDto.getNome())
                .idDiocese(setorDto.getIdDiocese())
                .createdByUser("SYSTEM")
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .isActive(setorDto.getIsActive())
                .build();
    }

    public SetorDto mapToDto(Setor setor) {
        return SetorDto.builder()
                .id(setor.getId())
                .nome(setor.getNomeSetor())
                .idDiocese(setor.getIdDiocese())
                .isActive(setor.getIsActive())
                .build();
    }

    public List<SetorDto> mapToDto(List<Setor> setores) {
        return setores.stream()
                .map(this::mapToDto)
                .toList();
    }
}
