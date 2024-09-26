package br.com.verbum.eccdiocesano.rest.mappers;

import br.com.verbum.eccdiocesano.domain.entities.Usuario;
import br.com.verbum.eccdiocesano.rest.dtos.UsuarioDto;
import br.com.verbum.eccdiocesano.rest.enums.Roles;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static br.com.verbum.eccdiocesano.rest.enums.Roles.*;

@Component
@AllArgsConstructor
public class UsuarioMapper {

    public Usuario mapToEntity(UsuarioDto usuarioDto) {
        return Usuario.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .telefone(usuarioDto.getTelefone())
                .papel(usuarioDto.getPapel()) //TODO consertar mapper
                .build();
    }

    public UsuarioDto mapToDto(Usuario usuario) {
        return UsuarioDto.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .telefone(usuario.getTelefone())
                .papel(usuario.getPapel())
                .build();
    }

    private String mapPapel(Roles papel) {
        return switch (papel) {
            case ADMIN -> "Administrador";
            case PAROQUIAL -> "Paroquial";
            case SETORIAL -> "Setorial";
            case DIOCESANO -> "Diocesano";
            default -> null;
        };
    }

    public Roles mapPapelToEntity(String papel) {
        return switch (papel) {
            case "Administrador" -> ADMIN;
            case "Paroquial" -> PAROQUIAL;
            case "Setorial" -> SETORIAL;
            case "Diocesano" -> DIOCESANO;
            default -> null;
        };
    }

    public List<UsuarioDto> mapToDto(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::mapToDto)
                .toList();
    }
}
