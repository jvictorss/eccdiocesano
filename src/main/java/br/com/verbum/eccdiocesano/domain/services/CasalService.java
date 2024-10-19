package br.com.verbum.eccdiocesano.domain.services;

import br.com.verbum.eccdiocesano.domain.repository.*;
import br.com.verbum.eccdiocesano.rest.dtos.CasalDto;
import br.com.verbum.eccdiocesano.rest.mappers.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
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
    private final ParoquiaMapper paroquiaMapper;
    private final SetorMapper setorMapper;
    private final DioceseMapper dioceseMapper;
    private final ParoquiaRepository paroquiaRepository;
    private final SetorRepository setorRepository;
    private final DioceseRepository dioceseRepository;
    private final PdfService pdfService;

    @Transactional
    public ResponseEntity<CasalDto> createCasal(CasalDto casalDto) {

        var existingCouple = repository.findCasalByEleCpfAndElaCpf(casalDto.getEle().getCpf(), casalDto.getEla().getCpf());
        if (existingCouple.isPresent()) {
            throw new EntityExistsException("Já existe o cadastro para este casal");
        }

        var ele = conjugeRepository.save(conjugeMapper.mapConjugeToEntity(casalDto.getEle())).getId();
        var ela = conjugeRepository.save(conjugeMapper.mapConjugeToEntity(casalDto.getEla())).getId();

        var eleId = conjugeRepository.findById(ele).orElseThrow();
        var elaId = conjugeRepository.findById(ela).orElseThrow();

        var mappedCasal = mapper.mapToEntity(casalDto);
        mappedCasal.setEle(eleId);
        mappedCasal.setEla(elaId);
        mappedCasal.setParoquiaEcc(paroquiaRepository.findById(casalDto.getParoquiaEcc()).orElseThrow());
        mappedCasal.setParoquiaAtual(paroquiaRepository.findById(casalDto.getParoquiaAtual()).orElseThrow());
        mappedCasal.setSetorial(setorRepository.findById(casalDto.getIdSetor()).orElseThrow());
        mappedCasal.setDiocese(dioceseRepository.findById(casalDto.getIdDiocese()).orElseThrow());
        mappedCasal.setCreatedAt(OffsetDateTime.now());

        var conjuge1 = mappedCasal.getEle();
        var conjuge2 = mappedCasal.getEla();

        conjuge1.setCasal(mappedCasal);
        conjuge2.setCasal(mappedCasal);
        conjuge1.setCreatedAt(OffsetDateTime.now());
        conjuge2.setCreatedAt(OffsetDateTime.now());
        conjuge1.setIsActive(mappedCasal.getIsActive());
        conjuge2.setIsActive(mappedCasal.getIsActive());

        conjugeRepository.save(conjuge1);
        conjugeRepository.save(conjuge2);

        return ResponseEntity.ok(mapper.mapToDto(repository.save(mappedCasal)));
    }

    public ResponseEntity<CasalDto> findById(UUID dioceseId) {

        var casal = repository.findById(dioceseId).orElseThrow();

        return ResponseEntity.ok(mapper.mapToDto(casal));
    }

    @Transactional
    public ResponseEntity<CasalDto> updateCasal(UUID casalId, CasalDto casalDto) {
//        var logger = LoggerFactory.getLogger(CasalService.class);
//
//        var casal = repository.findById(casalId).orElseThrow(() -> {
//            logger.error("Diocese with ID {} not found", casalId);
//            return new EntityNotFoundException("Diocese not found");
//        });
//
//        Map<String, Runnable> actions = new HashMap<>();
//        actions.put("nomeEsposo", () -> casal.getEle().setNome(Objects.toString(parameters.get("nomeEsposo"))));
//        actions.put("nomeEsposa", () -> casal.getEla().setNome(Objects.toString(parameters.get("nomeEsposa"))));
//        actions.put("dataCasamentoReligioso", () -> casal.setDataCasamentoReligioso(Objects.toString(parameters.get("dataCasamentoReligioso"))));
//        actions.put("dataCasamentoCivil", () -> casal.setDataCasamentoCivil(Objects.toString(parameters.get("dataCasamentoCivil"))));
//        actions.put("endereco", () -> casal.setEndereco(Objects.toString(parameters.get("endereco"))));
//        actions.put("bairro", () -> casal.setBairro(Objects.toString(parameters.get("bairro"))));
//        actions.put("cidade", () -> casal.setCidade(Objects.toString(parameters.get("cidade"))));
//        actions.put("estado", () -> casal.setEstado(Objects.toString(parameters.get("estado"))));
//        actions.put("paroquiaEcc", () -> casal.setParoquiaEcc(paroquiaMapper.mapToEntity((ParoquiaDto) parameters.get("paroquiaEcc"))));
//        actions.put("paroquiaAtual", () -> casal.setParoquiaAtual(paroquiaMapper.mapToEntity((ParoquiaDto) parameters.get("paroquiaAtual"))));
//        actions.put("idSetor", () -> casal.setSetorial(setorMapper.mapToEntity((SetorDto) parameters.get("idSetor"))));
//        actions.put("idDiocese", () -> casal.setDiocese(dioceseMapper.mapToEntity((DioceseDto) parameters.get("idDiocese"))));
//        actions.put("dataPrimeiraEtapa", () -> casal.setDataPrimeiraEtapa(Objects.toString(parameters.get("dataPrimeiraEtapa"))));
//        actions.put("dataSegundaEtapa", () -> casal.setDataSegundaEtapa(Objects.toString(parameters.get("dataSegundaEtapa"))));
//        actions.put("dataTerceiraEtapa", () -> casal.setDataTerceiraEtapa(Objects.toString(parameters.get("dataTerceiraEtapa"))));
//        actions.put("isActive", () -> casal.setIsActive(Boolean.parseBoolean(Objects.toString(parameters.get("isActive")))));
//
//        parameters.forEach((key, value) -> {
//            Runnable action = actions.get(key);
//            if (action != null) {
//                action.run();
//            }
//        });

//        var ele = conjugeRepository.save(conjugeMapper.mapConjugeToEntity(casalDto.getEle())).getId();
//        var ela = conjugeRepository.save(conjugeMapper.mapConjugeToEntity(casalDto.getEla())).getId();
//
//        var eleId = conjugeRepository.findById(ele).orElseThrow();
//        var elaId = conjugeRepository.findById(ela).orElseThrow();

        var casal = mapper.mapToEntity(casalDto);
        casal.setId(casalId);
        casal.setUpdatedAt(OffsetDateTime.now());

        var updatedCasal = repository.save(casal);

        return ResponseEntity.ok(mapper.mapToDto(updatedCasal));
    }

    public void deleteCasal(UUID casalId) {
        var logger = LoggerFactory.getLogger(DioceseService.class);

        var casal = repository.findById(casalId).orElseThrow(() -> {
            logger.error("Casal with ID {} not found", casalId);
            return new EntityNotFoundException("Casal not found");
        });

        repository.delete(casal);
    }

    public ResponseEntity<Iterable<CasalDto>> findAll(boolean isActive) {
        var casais = repository.findAllByIsActive(isActive);

        return ResponseEntity.ok(mapper.mapToDto(casais));
    }

    public byte[] findAllPrimeiraEtapaAndParoquia(UUID paroquiaId) throws IOException {
        var casais = repository.getCasaisPrimeiraEtapaPorParoquia(paroquiaId);

        var listaCasais = mapper.mapFromQuerySteps(casais);

        return pdfService.generateCouplesFormPdf(listaCasais, listaCasais.get(0).getParoquiaNome(), "Relatório de Casais para Segunda Etapa");
    }

    public byte[] findAllSegundaEtapaAndParoquia(UUID paroquiaId) throws IOException {
        var casais = repository.getCasaisSegundaEtapaPorParoquia(paroquiaId);

        var listaCasais = mapper.mapFromQuerySteps(casais);

        return pdfService.generateCouplesFormPdf(listaCasais, listaCasais.get(0).getParoquiaNome(), "Relatório de Casais para Terceira Etapa");
    }

    public byte[] findAllCouplesWithoutMatrimonySacrament(UUID paroquiaId) throws IOException {
        var casais = repository.getCasaisSemSacramentoDoMatrimonio(paroquiaId);

        var listaCasais = mapper.mapFromQueryWithoutSacrament(casais);

        return pdfService.generateCouplesWithoutSacrament(listaCasais, listaCasais.get(0).getParoquiaNome());
    }
}
