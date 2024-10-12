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
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CasalMapper {

    private final SetorMapper setorMapper;
    private final DioceseMapper dioceseMapper;

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
                .paroquiaEcc(casal.getParoquiaEcc().getId())
                .paroquiaAtual(casal.getParoquiaAtual().getId())
                .idSetor(setorMapper.mapToDto(casal.getSetorial()).getId())
                .idDiocese(dioceseMapper.mapToDto(casal.getDiocese()).getId())
                .dataPrimeiraEtapa(casal.getDataPrimeiraEtapa())
                .dataSegundaEtapa(casal.getDataSegundaEtapa())
                .dataTerceiraEtapa(casal.getDataTerceiraEtapa())
                .isActive(casal.getIsActive())
                .build();
    }

    public ConjugeDto mapConjuge(Conjuge conjuge) {
        return ConjugeDto.builder()
                .id(conjuge.getId())
                .nome(conjuge.getNome())
                .cpf(conjuge.getCpf())
                .apelido(conjuge.getApelido())
                .dataNascimento(conjuge.getDataNascimento())
                .email(conjuge.getEmail())
                .telefone(conjuge.getTelefone())
                .build();
    }

    public Conjuge mapConjugeToEntity(ConjugeDto conjugeDto) {
        return Conjuge.builder()
                .nome(conjugeDto.getNome())
                .cpf(conjugeDto.getCpf())
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

    public List<CasalResponseDto> mapFromQueryFirstStep(List<Map<String, Object>> results) {
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
            .build()).collect(Collectors.toList());
    }
}
