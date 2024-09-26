package br.com.verbum.eccdiocesano.rest.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CasalDto {

    private UUID id;
    @NotNull private ConjugeDto ele;
    @NotNull private ConjugeDto ela;
    private String dataCasamentoReligioso;
    @NotNull private String dataCasamentoCivil;
    @NotNull private String endereco;
    @NotNull private String bairro;
    @NotNull private String cidade;
    @NotNull private String estado;
    @NotNull private UUID paroquiaEcc;
    @NotNull private UUID paroquiaAtual;
    @NotNull private UUID idSetor;
    @NotNull private UUID idDiocese;
    @NotNull private String dataPrimeiraEtapa;
    private String dataSegundaEtapa;
    private String dataTerceiraEtapa;
    private Boolean isActive;
}
