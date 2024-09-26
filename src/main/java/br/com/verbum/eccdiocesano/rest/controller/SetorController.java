package br.com.verbum.eccdiocesano.rest.controller;

import br.com.verbum.eccdiocesano.domain.services.SetorService;
import br.com.verbum.eccdiocesano.rest.dtos.SetorDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "v1/setorial", produces = MediaType.APPLICATION_JSON_VALUE)
public class SetorController {

    private final SetorService service;

    @PostMapping
    public ResponseEntity<SetorDto> createSetor(@Valid @RequestBody SetorDto setorDto) {

        var setor = service.createSetor(setorDto);

        return ResponseEntity.ok(setor.getBody());
    }

    @GetMapping("/{setorId}")
    public ResponseEntity<SetorDto> getSetorById(@PathVariable UUID setorId) {

        var setor = service.findById(setorId);

        return ResponseEntity.ok(setor.getBody());
    }

    @PatchMapping("/{setorId}")
    public ResponseEntity<SetorDto> updateSetor(@PathVariable UUID setorId, @RequestBody Map<String, Object> parameters) {

        var setor = service.updateSetor(setorId, parameters);

        return ResponseEntity.ok(setor.getBody());
    }

    @DeleteMapping("/{setorId}")
    public ResponseEntity<Void> deleteDiocese(@PathVariable UUID setorId) {

        service.deleteSetor(setorId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/isActive={isActive}")
    public ResponseEntity<Iterable<SetorDto>> getAllDioceses(@PathVariable boolean isActive) {

        var setores = service.findAll(isActive);

        return ResponseEntity.ok(setores.getBody());
    }
}
