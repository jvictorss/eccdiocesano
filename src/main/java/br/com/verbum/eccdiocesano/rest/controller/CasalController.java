package br.com.verbum.eccdiocesano.rest.controller;


import br.com.verbum.eccdiocesano.domain.services.CasalService;
import br.com.verbum.eccdiocesano.rest.dtos.CasalDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

//@RestController
@Controller
@AllArgsConstructor
@RequestMapping(value = "v1/casal", produces = MediaType.APPLICATION_JSON_VALUE)
public class CasalController {

    private final CasalService service;

    @PostMapping("/create")
    public ResponseEntity<CasalDto> createCasal(@Valid @RequestBody CasalDto casalDto) {

        var casal = service.createCasal(casalDto);

        return ResponseEntity.ok(casal.getBody());
    }

    @GetMapping("/get/{casalId}")
    public ResponseEntity<CasalDto> getCasalById(@PathVariable UUID casalId) {

        var casal = service.findById(casalId);

        return ResponseEntity.ok(casal.getBody());
    }

    @GetMapping("/update/{casalId}")
    public String getCasalToUpdate(@PathVariable UUID casalId, Model model) {

        var casal = service.findById(casalId);
        model.addAttribute("casal", casal.getBody());

        return "update_casal";
    }

    @GetMapping("/{casalId}")
    public String getCasalById(@PathVariable UUID casalId, Model model) {

        var casal = service.findById(casalId);
        model.addAttribute("casal", casal.getBody());

        return "casal_visualizacao";
    }

    @PatchMapping("/{casalId}")
    public ResponseEntity<CasalDto> updateCasal(@PathVariable UUID casalId, @RequestBody CasalDto casalDto) {

        var casal = service.updateCasal(casalId, casalDto);

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
}
