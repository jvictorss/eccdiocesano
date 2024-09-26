package br.com.verbum.eccdiocesano.rest.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParoquiaDto {

    private UUID id;
    @NotNull private String nome;
    @NotNull private String cidade;
    @NotNull private String estado;
    @NotNull private UUID idSetor;
    @NotNull private UUID idDiocese;
//    @NotNull private Long tenantId;
    @NotNull private Boolean isActive;
}
