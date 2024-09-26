package br.com.verbum.eccdiocesano.rest.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SetorDto {

        private UUID id;
        @NotNull private String nome;
        @NotNull private UUID idDiocese;
        private Boolean isActive;
}
