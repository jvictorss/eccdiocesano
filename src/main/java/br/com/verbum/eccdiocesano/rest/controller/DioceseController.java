package br.com.verbum.eccdiocesano.rest.controller;


import br.com.verbum.eccdiocesano.domain.services.DioceseService;
import br.com.verbum.eccdiocesano.rest.dtos.DioceseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = "v1/diocese", produces = MediaType.APPLICATION_JSON_VALUE)
public class DioceseController {

    private final DioceseService service;

    @PostMapping("/create")
    public ResponseEntity<DioceseDto> createDiocese(@Valid @RequestBody DioceseDto dioceseDto) {

        var diocese = service.createDiocese(dioceseDto);

        return ResponseEntity.ok(diocese.getBody());
    }

    @GetMapping("/{dioceseId}")
    public ResponseEntity<DioceseDto> getDioceseById(@PathVariable UUID dioceseId) {

        var diocese = service.findById(dioceseId);

        return ResponseEntity.ok(diocese.getBody());
    }

    @PatchMapping("/{dioceseId}")
    public ResponseEntity<DioceseDto> updateDiocese(@PathVariable UUID dioceseId, @RequestBody Map<String, Object> parameters) {

        var diocese = service.updateDiocese(dioceseId, parameters);

        return ResponseEntity.ok(diocese.getBody());
    }

    @DeleteMapping("/{dioceseId}")
    public ResponseEntity<Void> deleteDiocese(@PathVariable UUID dioceseId) {

        service.deleteDiocese(dioceseId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/isActive={isActive}")
    public ResponseEntity<Iterable<DioceseDto>> getAllDioceses(@PathVariable boolean isActive) {

        var dioceses = service.findAll(isActive);

        return ResponseEntity.ok(dioceses.getBody());
    }
}
