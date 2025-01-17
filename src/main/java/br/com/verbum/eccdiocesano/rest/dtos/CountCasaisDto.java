package br.com.verbum.eccdiocesano.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountCasaisDto {

    private String countPrimeiraEtapa;
    private String countSegundaEtapa;
    private String countTerceiraEtapa;
    private String countSemSacramento;
    private String totalActiveCouples;
    private String totalInactiveCouples;
}
