package br.com.verbum.eccdiocesano.rest.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ConjugeDto {

    private UUID id;
    @NotNull private String nome;
    @NotNull private String apelido;
    @NotNull private String telefone;
    @NotNull private String email;
    @NotNull private String cpf;
    @NotNull private String dataNascimento;
}
