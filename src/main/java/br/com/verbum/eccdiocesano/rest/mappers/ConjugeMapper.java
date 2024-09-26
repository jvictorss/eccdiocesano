package br.com.verbum.eccdiocesano.rest.mappers;

import br.com.verbum.eccdiocesano.domain.entities.Casal;
import br.com.verbum.eccdiocesano.domain.entities.Conjuge;
import br.com.verbum.eccdiocesano.rest.dtos.CasalDto;
import br.com.verbum.eccdiocesano.rest.dtos.ConjugeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ConjugeMapper {

    public ConjugeDto mapConjuge(Conjuge conjuge) {
        return ConjugeDto.builder()
                .id(conjuge.getId())
                .nome(conjuge.getNome())
                .cpf(conjuge.getCpf())
                .apelido(conjuge.getApelido())
                .dataNascimento(conjuge.getDataNascimento())
                .email(conjuge.getEmail())
                .telefone(conjuge.getTelefone())
                .build();
    }

    public Conjuge mapConjugeToEntity(ConjugeDto conjugeDto) {
        return Conjuge.builder()
                .nome(conjugeDto.getNome())
                .cpf(conjugeDto.getCpf())
                .apelido(conjugeDto.getApelido())
                .dataNascimento(conjugeDto.getDataNascimento())
                .email(conjugeDto.getEmail())
                .telefone(conjugeDto.getTelefone())
                .build();
    }
}
