package br.com.verbum.eccdiocesano.domain.services;

import br.com.verbum.eccdiocesano.domain.repository.ParoquiaRepository;
import br.com.verbum.eccdiocesano.domain.repository.SetorRepository;
import br.com.verbum.eccdiocesano.exception.BusinessException;
import br.com.verbum.eccdiocesano.rest.dtos.SetorDto;
import br.com.verbum.eccdiocesano.rest.mappers.SetorMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SetorService {

    private final SetorRepository repository;
    private final SetorMapper mapper;
    private final SetorMapper setorMapper;
    private final ParoquiaRepository paroquiaRepository;

    public ResponseEntity<SetorDto> findById(UUID setorId) {

            var setor = repository.findById(setorId).orElseThrow();

            return ResponseEntity.ok(mapper.mapToDto(setor));
    }

    public ResponseEntity<SetorDto> createSetor(SetorDto setorDto) {

        var mappedSetor = mapper.mapToEntity(setorDto);
        mappedSetor.setCreatedAt(OffsetDateTime.now());
        mappedSetor.setCreatedByUser("SYSTEM");

        var setor = repository.save(mappedSetor);

        return ResponseEntity.ok(mapper.mapToDto(setor));
    }

    public ResponseEntity<SetorDto> updateSetor(UUID setorId, Map<String, Object> parameters) {

        var setor = repository.findById(setorId).orElseThrow();

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            switch (entry.getKey()) {
                case "nome":
                    setor.setNomeSetor((String) entry.getValue());
                    break;
                case "idDiocese":
                    setor.setIdDiocese((UUID) entry.getValue());
                    break;
                case "isActive":
                    setor.setIsActive((Boolean) entry.getValue());
                    break;
                default:
                    break;}
        }

        setor.setUpdatedAt(OffsetDateTime.now());

        repository.save(setor);

        return ResponseEntity.ok(mapper.mapToDto(setor));
    }

    public void deleteSetor(UUID setorId) throws BusinessException {

        int count = paroquiaRepository.findParoquiaByIdSetor(setorId);

        if (count > 0)
            throw new BusinessException("Não é possível excluir o Setorial. Há paróquias cadastrados com ela.");

        var setor = repository.findById(setorId).orElseThrow();

        repository.delete(setor);
    }

    public ResponseEntity<Iterable<SetorDto>> findAll(boolean isActive) {
        var setores = repository.findAllByIsActive(isActive);

        return ResponseEntity.ok(setorMapper.mapToDto(setores));
    }
}
