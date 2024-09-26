package br.com.verbum.eccdiocesano.rest.controller;

import br.com.verbum.eccdiocesano.domain.services.UsuarioService;
import br.com.verbum.eccdiocesano.exception.CantSaveException;
import br.com.verbum.eccdiocesano.exception.PasswordInvalidException;
import br.com.verbum.eccdiocesano.exception.UserNotFoundException;
import br.com.verbum.eccdiocesano.rest.dtos.UsuarioDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping("/create")
    public ResponseEntity<Void> createUsuario(@Valid @RequestBody UsuarioDto usuarioDto) throws UserNotFoundException, PasswordInvalidException, CantSaveException {

        return new ResponseEntity<>(service.createUsuario(usuarioDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<UsuarioDto>> getAllCasais(@RequestParam boolean isActive) {

        var casais = service.findAll(isActive);

        return ResponseEntity.ok(casais);
    }
}
