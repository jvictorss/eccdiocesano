package br.com.verbum.eccdiocesano.rest.mappers;

import br.com.verbum.eccdiocesano.domain.entities.Casal;
import br.com.verbum.eccdiocesano.domain.entities.Conjuge;
import br.com.verbum.eccdiocesano.rest.dtos.CasalDto;
import br.com.verbum.eccdiocesano.rest.dtos.CasalResponseDto;
import br.com.verbum.eccdiocesano.rest.dtos.ConjugeDto;
import br.com.verbum.eccdiocesano.rest.dtos.CountCasaisDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class CountMapper {

    public CountCasaisDto mapFromQueryCountCasais(Map<String, Object> results) {
    return CountCasaisDto.builder()
            .countPrimeiraEtapa(results.get("count_primeira_etapa") != null ? results.get("count_primeira_etapa").toString() : "")
            .countSegundaEtapa(results.get("count_segunda_etapa") != null ? results.get("count_segunda_etapa").toString() : "")
            .countTerceiraEtapa(results.get("count_terceira_etapa") != null ? results.get("count_terceira_etapa").toString() : "")
            .countSemSacramento(results.get("count_sem_sacramento") != null ? results.get("count_sem_sacramento").toString() : "")
            .totalActiveCouples(results.get("total_active_couples") != null ? results.get("total_active_couples").toString() : "")
            .totalInactiveCouples(results.get("total_inactive_couples") != null ? results.get("total_inactive_couples").toString() : "")
            .build();
    }
}
