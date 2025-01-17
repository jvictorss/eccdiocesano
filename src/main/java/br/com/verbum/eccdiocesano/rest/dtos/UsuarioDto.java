package br.com.verbum.eccdiocesano.rest.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String papel;
}
