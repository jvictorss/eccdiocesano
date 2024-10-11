package br.com.verbum.eccdiocesano.rest.controller;


import br.com.verbum.eccdiocesano.domain.services.CasalService;
import br.com.verbum.eccdiocesano.rest.dtos.CasalDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

//@RestController
@Controller
@AllArgsConstructor
@RequestMapping(value = "v1/casal", produces = MediaType.APPLICATION_JSON_VALUE)
public class CasalController {

    private final CasalService service;

    @PostMapping("/create")
    public ResponseEntity<CasalDto> createCasal(@Valid @RequestBody CasalDto casalDto) {

        // TODO regex para CPF e Celular

        var casal = service.createCasal(casalDto);

        return ResponseEntity.ok(casal.getBody());
    }

//    @GetMapping("/{casalId}")
//    public ResponseEntity<CasalDto> getCasalById(@PathVariable UUID casalId) {
//
//        var casal = service.findById(casalId);
//
//        return ResponseEntity.ok(casal.getBody());
//    }

    @GetMapping("/{casalId}")
    public String getCasalById(@PathVariable UUID casalId, Model model) {

        var casal = service.findById(casalId);
        model.addAttribute("casal", casal.getBody());

        return "casal_visualizacao";
    }

    @PatchMapping("/{casalId}")
    public ResponseEntity<CasalDto> updateCasal(@PathVariable UUID casalId, @RequestBody Map<String, Object> parameters) {

        var casal = service.updateCasal(casalId, parameters);

        return ResponseEntity.ok(casal.getBody());
    }

    @DeleteMapping("/{casalId}")
    public ResponseEntity<Void> deleteCasal(@PathVariable UUID casalId) {

        service.deleteCasal(casalId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<CasalDto>> getAllCasais(@RequestParam boolean isActive) {

        var casais = service.findAll(isActive);

        return ResponseEntity.ok(casais.getBody());
    }

    @PatchMapping("/transferencia-casal/{casalId}/paroquia/{novaParoquia}")
    public ResponseEntity<CasalDto> transferenciaCasal(@PathVariable UUID casalId, @PathVariable UUID novaParoquia) {

        // no service é feito um findById para pegar o novo setor (sendo o caso) e a nova diocese.
        return null;

    }
}
