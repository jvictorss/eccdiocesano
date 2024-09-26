package br.com.verbum.eccdiocesano.domain.services;

import br.com.verbum.eccdiocesano.domain.repository.DioceseRepository;
import br.com.verbum.eccdiocesano.rest.dtos.DioceseDto;
import br.com.verbum.eccdiocesano.rest.mappers.DioceseMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DioceseService {

    private final DioceseRepository repository;
    private final DioceseMapper dioceseMapper;

    public ResponseEntity<DioceseDto> createDiocese(DioceseDto dioceseDto) {

        var existingDiocese = repository.findByNomeDioceseAndCidadeDiocese(dioceseDto.getNome(), dioceseDto.getCidade());
        if (existingDiocese.isPresent()) {
            throw new EntityExistsException("Diocese already exists");
        }

        var mappedDiocese = dioceseMapper.mapToEntity(dioceseDto);
        mappedDiocese.setCreatedAt(OffsetDateTime.now());
        var diocese = repository.save(mappedDiocese);

        return ResponseEntity.ok(dioceseMapper.mapToDto(diocese));
    }

    public ResponseEntity<DioceseDto> findById(UUID dioceseId) {

        var diocese = repository.findById(dioceseId).orElseThrow();

        return ResponseEntity.ok(dioceseMapper.mapToDto(diocese));
    }

    public ResponseEntity<DioceseDto> updateDiocese(UUID dioceseId, Map<String, Object> parameters) {
        var logger = LoggerFactory.getLogger(DioceseService.class);

        var diocese = repository.findById(dioceseId).orElseThrow(() -> {
            logger.error("Diocese with ID {} not found", dioceseId);
            return new EntityNotFoundException("Diocese not found");
        });

        Map<String, Runnable> actions = new HashMap<>();
        actions.put("nome", () -> diocese.setNomeDiocese(Objects.toString(parameters.get("nome"))));
        actions.put("cidade", () -> diocese.setCidadeDiocese(Objects.toString(parameters.get("cidade"))));
        actions.put("estado", () -> diocese.setEstadoDiocese(Objects.toString(parameters.get("estado"))));
        actions.put("isActive", () -> diocese.setIsActive(Boolean.parseBoolean(Objects.toString(parameters.get("isActive")))));

        parameters.forEach((key, value) -> {
            Runnable action = actions.get(key);
            if (action != null) {
                action.run();
            }
        });

        diocese.setUpdatedAt(OffsetDateTime.now());

        var updatedDiocese = repository.save(diocese);

        return ResponseEntity.ok(dioceseMapper.mapToDto(updatedDiocese));
    }

    public void deleteDiocese(UUID dioceseId) {
        var logger = LoggerFactory.getLogger(DioceseService.class);

        var diocese = repository.findById(dioceseId).orElseThrow(() -> {
            logger.error("Diocese with ID {} not found", dioceseId);
            return new EntityNotFoundException("Diocese not found");
        });

        repository.delete(diocese);
    }

    public ResponseEntity<Iterable<DioceseDto>> findAll(boolean isActive) {
        var dioceses = repository.findAllByIsActive(isActive);

        return ResponseEntity.ok(dioceseMapper.mapToDto(dioceses));
    }
}
