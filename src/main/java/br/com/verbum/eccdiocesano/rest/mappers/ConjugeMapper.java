package br.com.verbum.eccdiocesano.rest.mappers;

import br.com.verbum.eccdiocesano.domain.entities.Casal;
import br.com.verbum.eccdiocesano.domain.entities.Conjuge;
import br.com.verbum.eccdiocesano.domain.entities.Paroquia;
import br.com.verbum.eccdiocesano.domain.entities.Setor;
import br.com.verbum.eccdiocesano.rest.dtos.CasalDto;
import br.com.verbum.eccdiocesano.rest.dtos.ConjugeDto;
import br.com.verbum.eccdiocesano.rest.dtos.ParoquiaDto;
import br.com.verbum.eccdiocesano.rest.dtos.SetorDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class CasalMapper {

    private final SetorMapper setorMapper;
    private final DioceseMapper dioceseMapper;

    public Casal mapToEntity(CasalDto casalDto) {
        return Casal.builder()
                .id(casalDto.getId())
                .ele(mapConjugeToEntity(casalDto.getEle()))
                .ela(mapConjugeToEntity(casalDto.getEla()))
                .dataCasamentoReligioso(casalDto.getDataCasamentoReligioso())
                .dataCasamentoCivil(casalDto.getDataCasamentoCivil())
                .endereco(casalDto.getEndereco())
                .bairro(casalDto.getBairro())
                .cidade(casalDto.getCidade())
//                .tenantId(casalDto.getTenantId())
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
                .ele(mapConjuge(casal.getEla()))
                .dataCasamentoReligioso(casal.getDataCasamentoReligioso())
                .dataCasamentoCivil(casal.getDataCasamentoCivil())
                .endereco(casal.getEndereco())
                .bairro(casal.getBairro())
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
}
