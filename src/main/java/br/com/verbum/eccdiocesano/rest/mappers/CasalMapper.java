package br.com.verbum.eccdiocesano.rest.mappers;

import br.com.verbum.eccdiocesano.domain.entities.Casal;
import br.com.verbum.eccdiocesano.domain.entities.Conjuge;
import br.com.verbum.eccdiocesano.rest.dtos.CasalDto;
import br.com.verbum.eccdiocesano.rest.dtos.CasalResponseDto;
import br.com.verbum.eccdiocesano.rest.dtos.ConjugeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Component
@AllArgsConstructor
public class CasalMapper {

    public Casal mapToEntity(CasalDto casalDto) {
        return Casal.builder()
                .id(casalDto.getId())
                .dataCasamentoReligioso(casalDto.getDataCasamentoReligioso())
                .dataCasamentoCivil(casalDto.getDataCasamentoCivil())
                .endereco(casalDto.getEndereco())
                .bairro(casalDto.getBairro())
                .cidade(casalDto.getCidade())
                .estado(casalDto.getEstado())
                .dataPrimeiraEtapa(casalDto.getDataPrimeiraEtapa())
                .dataSegundaEtapa(casalDto.getDataSegundaEtapa())
                .dataTerceiraEtapa(casalDto.getDataTerceiraEtapa())
                .paroquiaEcc(casalDto.getParoquiaEcc())
                .paroquiaAtual(casalDto.getParoquiaAtual())
                .isActive(casalDto.getIsActive())
                .build();
    }

    public CasalDto mapToDto(Casal casal) {
        return CasalDto.builder()
                .id(casal.getId())
                .ele(mapConjuge(casal.getEle()))
                .ela(mapConjuge(casal.getEla()))
                .dataCasamentoReligioso(casal.getDataCasamentoReligioso())
                .dataCasamentoCivil(casal.getDataCasamentoCivil())
                .endereco(casal.getEndereco())
                .bairro(casal.getBairro())
                .estado(casal.getEstado())
                .cidade(casal.getCidade())
                .dataPrimeiraEtapa(casal.getDataPrimeiraEtapa())
                .dataSegundaEtapa(casal.getDataSegundaEtapa())
                .dataTerceiraEtapa(casal.getDataTerceiraEtapa())
                .paroquiaEcc(casal.getParoquiaEcc())
                .paroquiaAtual(casal.getParoquiaAtual())
                .isActive(casal.getIsActive())
                .build();
    }

    public ConjugeDto mapConjuge(Conjuge conjuge) {
        return ConjugeDto.builder()
                .id(conjuge.getId())
                .nome(conjuge.getNome())
                .apelido(conjuge.getApelido())
                .dataNascimento(conjuge.getDataNascimento())
                .email(conjuge.getEmail())
                .telefone(conjuge.getTelefone())
                .build();
    }

    public Conjuge mapConjugeToEntity(ConjugeDto conjugeDto) {
        return Conjuge.builder()
                .nome(conjugeDto.getNome())
                .dataNascimento(conjugeDto.getDataNascimento())
                .email(conjugeDto.getEmail())
                .telefone(conjugeDto.getTelefone())
                .build();
    }

    public List<CasalDto> mapToDto(List<Casal> casais) {
        return casais.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<CasalResponseDto> mapFromQuerySteps(List<Map<String, Object>> results) {
    return results.stream().map(result -> CasalResponseDto.builder()
            .apelidoEle((String) result.get("apelidoEle"))
            .telefoneEle((String) result.get("telefoneEle"))
            .apelidoEla((String) result.get("apelidoEla"))
            .telefoneEla((String) result.get("telefoneEla"))
            .dataPrimeiraEtapa((String) result.get("data_primeira_etapa"))
            .dataSegundaEtapa((String) result.get("data_segunda_etapa"))
            .dataTerceiraEtapa((String) result.get("data_terceira_etapa"))
            .paroquiaNome((String) result.get("paroquiaNome"))
            .isActive((Boolean) result.get("is_active"))
            .build()).collect(toList());
    }

    public List<CasalResponseDto> mapFromQueryWithoutSacrament(List<Map<String, Object>> results) {
        return results.stream().map(result -> CasalResponseDto.builder()
                .apelidoEle((String) result.get("apelidoEle"))
                .telefoneEle((String) result.get("telefoneEle"))
                .apelidoEla((String) result.get("apelidoEla"))
                .telefoneEla((String) result.get("telefoneEla"))
                .casamentoCivil((String) result.get("casamentoCivil"))
                .paroquiaNome((String) result.get("paroquiaNome"))
                .isActive((Boolean) result.get("is_active"))
                .build()).collect(toList());
    }
}
