package br.com.verbum.eccdiocesano.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuantidadeCasaisParoquiaDto {

    private CountCasaisDto quanitdades;
    private List<CasalResponseDto> casais;

}
