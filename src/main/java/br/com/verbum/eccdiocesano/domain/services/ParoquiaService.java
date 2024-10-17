package br.com.verbum.eccdiocesano.domain.services;

import br.com.verbum.eccdiocesano.domain.entities.Casal;
import br.com.verbum.eccdiocesano.domain.repository.CasalRepository;
import br.com.verbum.eccdiocesano.domain.repository.ParoquiaRepository;
import br.com.verbum.eccdiocesano.exception.BusinessException;
import br.com.verbum.eccdiocesano.rest.dtos.DioceseDto;
import br.com.verbum.eccdiocesano.rest.dtos.ParoquiaDto;
import br.com.verbum.eccdiocesano.rest.mappers.ParoquiaMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class ParoquiaService {

    private final ParoquiaRepository repository;
    private final CasalRepository casalRepository;
    private final ParoquiaMapper mapper;

    public ResponseEntity<ParoquiaDto> findById(UUID paroquiaId) {

            var paroquia = repository.findById(paroquiaId).orElseThrow();

            return ResponseEntity.ok(mapper.mapToDto(paroquia));
    }

    public ResponseEntity<ParoquiaDto> createParoquia(ParoquiaDto paroquiaDto) {

        var existingParoquia = repository.findByNomeAndCidade(paroquiaDto.getNome(), paroquiaDto.getCidade());
        if (existingParoquia.isPresent()) {
            throw new EntityExistsException("Já existe uma paróquia com este nome nesta cidade");
        }

        var mappedParoquia = mapper.mapToEntity(paroquiaDto);
        mappedParoquia.setCreatedAt(OffsetDateTime.now());
        var paroquia = repository.save(mappedParoquia);

        return ResponseEntity.ok(mapper.mapToDto(paroquia));
    }

    public ResponseEntity<ParoquiaDto> updateParoquia(UUID paroquiaId, Map<String, Object> parameters) {
        var logger = LoggerFactory.getLogger(DioceseService.class);

        var paroquia = repository.findById(paroquiaId).orElseThrow(() -> {
            logger.error("Diocese with ID {} not found", paroquiaId);
            return new EntityNotFoundException("Diocese not found");
        });

        Map<String, Runnable> actions = new HashMap<>();
        actions.put("nome", () -> paroquia.setNome(Objects.toString(parameters.get("nome"))));
        actions.put("cidade", () -> paroquia.setCidade(Objects.toString(parameters.get("cidade"))));
        actions.put("estado", () -> paroquia.setEstado(Objects.toString(parameters.get("estado"))));
        actions.put("idSetor", () -> paroquia.setIdSetor(UUID.fromString(Objects.toString(parameters.get("idSetor")))));
        actions.put("idDiocese", () -> paroquia.setIdDiocese(UUID.fromString(Objects.toString(parameters.get("idDiocese")))));
        actions.put("isActive", () -> paroquia.setIsActive(Boolean.parseBoolean(Objects.toString(parameters.get("isActive")))));

        parameters.forEach((key, value) -> {
            Runnable action = actions.get(key);
            if (action != null) {
                action.run();
            }
        });

        paroquia.setUpdatedAt(OffsetDateTime.now());

        var updatedParoquia = repository.save(paroquia);

        return ResponseEntity.ok(mapper.mapToDto(updatedParoquia));
    }

    public void deleteParoquia(UUID paroquiaId) throws BusinessException {

            Optional<Casal> temCasalNaParoquia = casalRepository.findCasalByParoquiaAtualId(paroquiaId);

            if (temCasalNaParoquia.isPresent())
                throw new BusinessException("Não é possível excluir a paróquia. Há casais cadastrados com ela.");

        repository.deleteById(paroquiaId);
    }

    public ResponseEntity<Iterable<ParoquiaDto>> getAllParoquias(boolean isActive) {
        var paroquias = repository.findAllByIsActive(isActive);

        return ResponseEntity.ok(mapper.mapToDto(paroquias));
    }
}
