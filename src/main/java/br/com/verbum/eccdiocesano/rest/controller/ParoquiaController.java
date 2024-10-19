package br.com.verbum.eccdiocesano.rest.controller;

import br.com.verbum.eccdiocesano.domain.services.ParoquiaService;
import br.com.verbum.eccdiocesano.exception.BusinessException;
import br.com.verbum.eccdiocesano.rest.dtos.DioceseDto;
import br.com.verbum.eccdiocesano.rest.dtos.ParoquiaDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping(value = "v1/paroquia", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParoquiaController {

    private final ParoquiaService service;

    @PostMapping("/create")
    public ResponseEntity<ParoquiaDto> createParoquia(@Valid @RequestBody ParoquiaDto paroquiaDto) {

        var paroquia = service.createParoquia(paroquiaDto);

        return ResponseEntity.ok(paroquia.getBody());
    }

    @GetMapping("/get/{paroquiaId}")
    public ResponseEntity<ParoquiaDto> getParoquiaById(@PathVariable UUID paroquiaId) {

        var paroquia = service.findById(paroquiaId);

        return ResponseEntity.ok(paroquia.getBody());
    }

    @GetMapping("/{paroquiaId}")
    public String getParoquiaToUpdate(@PathVariable UUID paroquiaId, Model model) {
        var paroquia = service.findById(paroquiaId);
        model.addAttribute("paroquia", paroquia.getBody());

        return "update_paroquia";
    }

    @PatchMapping("/{paroquiaId}")
    public ResponseEntity<ParoquiaDto> updateParoquia(@PathVariable UUID paroquiaId, @RequestBody Map<String, Object> parameters) {

        var paroquia = service.updateParoquia(paroquiaId, parameters);

        return ResponseEntity.ok(paroquia.getBody());
    }

    @DeleteMapping("/{paroquiaId}")
    public ResponseEntity<Void> deleteParoquia(@PathVariable UUID paroquiaId) throws BusinessException {

        service.deleteParoquia(paroquiaId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/isActive={isActive}")
    public ResponseEntity<Iterable<ParoquiaDto>> getAllParoquias(@PathVariable boolean isActive) {
        var paroquias = service.getAllParoquias(isActive);

        return ResponseEntity.ok(paroquias.getBody());
    }
}
