package br.com.verbum.eccdiocesano.domain.services;

import br.com.verbum.eccdiocesano.domain.repository.CasalRepository;
import br.com.verbum.eccdiocesano.domain.repository.ConjugeRepository;
import br.com.verbum.eccdiocesano.rest.dtos.CasalDto;
import br.com.verbum.eccdiocesano.rest.mappers.CasalMapper;
import br.com.verbum.eccdiocesano.rest.mappers.ConjugeMapper;
import br.com.verbum.eccdiocesano.rest.mappers.CountMapper;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CasalService {

    private final CasalRepository repository;
    private final ConjugeRepository conjugeRepository;
    private final ConjugeMapper conjugeMapper;
    private final CasalMapper mapper;
    private final CountMapper countMapper;
    private final PdfService pdfService;

    private final static String PAROQUIA_DO_ROSARIO = "Nossa Senhora do Rosário";

    @Transactional
    public ResponseEntity<CasalDto> createCasal(CasalDto casalDto) {

        var existingCouple = repository.findCasalByEleTelefoneAndElaTelefone(casalDto.getEle().getTelefone(), casalDto.getEla().getTelefone());
        if (existingCouple.isPresent()) {
            throw new EntityExistsException("Já existe o cadastro para este casal");
        }

        var saveEle = conjugeRepository.save(conjugeMapper.mapConjugeToEntity(casalDto.getEle())).getId();
        var saveEla = conjugeRepository.save(conjugeMapper.mapConjugeToEntity(casalDto.getEla())).getId();

        var eleId = conjugeRepository.findById(saveEle).orElseThrow();
        var elaId = conjugeRepository.findById(saveEla).orElseThrow();

        var mappedCasal = mapper.mapToEntity(casalDto);
        mappedCasal.setEle(eleId);
        mappedCasal.setEla(elaId);
        mappedCasal.setCreatedAt(OffsetDateTime.now());
        mappedCasal.setParoquiaEcc(casalDto.getParoquiaEcc() == null ? PAROQUIA_DO_ROSARIO : casalDto.getParoquiaEcc());
        mappedCasal.setParoquiaAtual(PAROQUIA_DO_ROSARIO);

        var conjuge1 = mappedCasal.getEle();
        var conjuge2 = mappedCasal.getEla();

        conjuge1.setCasal(mappedCasal);
        conjuge2.setCasal(mappedCasal);
        conjuge1.setCreatedAt(OffsetDateTime.now());
        conjuge2.setCreatedAt(OffsetDateTime.now());

        var isActive = conjuge1.getDataFalecimento().isBlank() && conjuge2.getDataFalecimento().isBlank() && mappedCasal.getIsActive();
        conjuge1.setIsActive(isActive);
        conjuge2.setIsActive(isActive);
        mappedCasal.setIsActive(isActive);

        conjugeRepository.save(conjuge1);
        conjugeRepository.save(conjuge2);

        return ResponseEntity.ok(mapper.mapToDto(repository.save(mappedCasal)));
    }

    public ResponseEntity<CasalDto> findById(UUID casalId) {

        var casal = repository.findById(casalId).orElseThrow();

        return ResponseEntity.ok(mapper.mapToDto(casal));
    }

    @Transactional
    public ResponseEntity<CasalDto> updateCasal(UUID casalId, CasalDto casalDto) {
        var ele = conjugeRepository.save(conjugeMapper.mapConjugeToEntity(casalDto.getEle())).getId();
        var ela = conjugeRepository.save(conjugeMapper.mapConjugeToEntity(casalDto.getEla())).getId();

        var eleId = conjugeRepository.findById(ele).orElseThrow();
        var elaId = conjugeRepository.findById(ela).orElseThrow();

        var mappedCasal = mapper.mapToEntity(casalDto);
        mappedCasal.setEle(eleId);
        mappedCasal.setEla(elaId);

        mappedCasal.setId(casalId);
        mappedCasal.setUpdatedAt(OffsetDateTime.now());

        var updatedCasal = repository.save(mappedCasal);

        return ResponseEntity.ok(mapper.mapToDto(updatedCasal));
    }

    @Transactional
    public void deleteCasal(UUID casalId) {
        var casal = repository.findById(casalId).orElseThrow();
        conjugeRepository.deleteAllByCasalId(casalId);
        repository.delete(casal);
    }

    public ResponseEntity<Iterable<CasalDto>> findAll(boolean isActive) {
        var casais = repository.findAllByIsActive(isActive);
        return ResponseEntity.ok(mapper.mapToDto(casais));
    }

    public byte[] findAllPrimeiraEtapa() throws IOException {
        var casais = repository.getCasaisPrimeiraEtapa();
        var countCasais = countMapper.mapFromQueryCountCasais(repository.countCasaisPrimeiraEtapa());
        var listaCasais = mapper.mapFromQuerySteps(casais);

        return pdfService.generateCouplesFormPdf(listaCasais, countCasais,"Relatório de Casais ativos apenas com Primeira Etapa");
    }

    public byte[] findAllSegundaEtapa() throws IOException {
        var casais = repository.getCasaisSegundaEtapa();
        var countCasais = countMapper.mapFromQueryCountCasais(repository.countCasaisSegundaEtapa());
        var listaCasais = mapper.mapFromQuerySteps(casais);

        return pdfService.generateCouplesFormPdf(listaCasais, countCasais, "Relatório de Casais ativos com Primeira e Segunda Etapas");
    }

    public byte[] findAllTerceiraEtapa() throws IOException {
        var casais = repository.getCasaisTerceiraEtapa();
        var countCasais = countMapper.mapFromQueryCountCasais(repository.countCasaisTerceiraEtapa());
        var listaCasais = mapper.mapFromQuerySteps(casais);

        return pdfService.generateCouplesFormPdf(listaCasais, countCasais, "Relatório de Casais ativos com as Três Etapas");
    }

    public byte[] getInactiveCouples() throws IOException {
        var casais = repository.getInactiveCouples();
        var countCasais = countMapper.mapFromQueryCountCasais(repository.countInactiveCouples());
        var listaCasais = mapper.mapFromQuerySteps(casais);

        return pdfService.generateCouplesFormPdf(listaCasais, countCasais, "Relatório de Casais Inativos");
    }

    public byte[] getActiveCouples() throws IOException {
        var casais = repository.getActiveCouples();
        var countCasais = countMapper.mapFromQueryCountCasais(repository.countActiveCouples());
        var listaCasais = mapper.mapFromQuerySteps(casais);

        return pdfService.generateCouplesFormPdf(listaCasais, countCasais, "Relatório de Casais Ativos");
    }

    public byte[] findAllCouplesWithoutMatrimonySacrament() throws IOException {
        var casais = repository.getCasaisSemSacramentoDoMatrimonio();
        var countCasais = countMapper.mapFromQueryCountCasais(repository.countCasaisSemSacramentoDoMatrimonio());
        var listaCasais = mapper.mapFromQueryWithoutSacrament(casais);

        return pdfService.generateCouplesWithoutSacrament(listaCasais, countCasais);
    }
}
