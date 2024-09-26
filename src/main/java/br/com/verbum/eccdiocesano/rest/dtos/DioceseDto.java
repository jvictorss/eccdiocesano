package br.com.verbum.eccdiocesano.rest.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DioceseDto {

    private UUID id;
    @NotNull private String nome;
    @NotNull private String cidade;
    @NotNull private String estado;
    @NotNull private Boolean isActive;
    private List<SetorDto> setores;
}
