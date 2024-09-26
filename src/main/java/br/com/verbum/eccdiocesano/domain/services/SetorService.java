package br.com.verbum.eccdiocesano.domain.services;

import br.com.verbum.eccdiocesano.domain.repository.CasalRepository;
import br.com.verbum.eccdiocesano.rest.dtos.CasalDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CasalService {

    private final CasalRepository repository;

    public ResponseEntity<CasalDto> findById(UUID casalId) {

            var casal = repository.findById(casalId);

            return ResponseEntity.ok(casal);
    }
}
