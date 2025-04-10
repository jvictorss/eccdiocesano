package br.com.verbum.eccdiocesano.rest.mappers;

import br.com.verbum.eccdiocesano.domain.entities.Casal;
import br.com.verbum.eccdiocesano.domain.entities.Conjuge;
import br.com.verbum.eccdiocesano.rest.dtos.CasalDto;
import br.com.verbum.eccdiocesano.rest.dtos.ConjugeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ConjugeMapper {

    public ConjugeDto mapConjuge(Conjuge conjuge) {
        return ConjugeDto.builder()
                .id(conjuge.getId())
                .nome(conjuge.getNome())
                .apelido(conjuge.getApelido())
                .dataNascimento(conjuge.getDataNascimento())
                .email(conjuge.getEmail())
                .telefone(conjuge.getTelefone())
                .dataFalecimento(conjuge.getDataFalecimento())
                .build();
    }

    public Conjuge mapConjugeToEntity(ConjugeDto conjugeDto) {
        return Conjuge.builder()
                .nome(conjugeDto.getNome())
                .apelido(conjugeDto.getApelido())
                .dataNascimento(conjugeDto.getDataNascimento())
                .dataFalecimento(conjugeDto.getDataFalecimento())
                .email(conjugeDto.getEmail())
                .telefone(conjugeDto.getTelefone())
                .updatedAt(OffsetDateTime.now())
                .build();
    }
}
