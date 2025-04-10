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
public class CasalResponseDto {

    private String apelidoEle;
    private String telefoneEle;
    private String apelidoEla;
    private String telefoneEla;
    private String dataPrimeiraEtapa;
    private String dataSegundaEtapa;
    private String dataTerceiraEtapa;
    private String casamentoCivil;
    private String paroquiaNome;
    private boolean isActive;
}
