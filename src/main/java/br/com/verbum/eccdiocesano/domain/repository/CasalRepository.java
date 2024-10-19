package br.com.verbum.eccdiocesano.domain.repository;

import br.com.verbum.eccdiocesano.domain.entities.Casal;
import br.com.verbum.eccdiocesano.domain.reuse.BaseRepository;
import br.com.verbum.eccdiocesano.rest.dtos.CasalResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CasalRepository extends BaseRepository<Casal> {

    // procurar todos os casais da paróquia que estão ativos

    // query escrita para procurar casais da paróquia entre ativos e inativos;

    // query escrita para procurar casais que foram transferidos;

    Optional<Casal> findCasalByEleCpfAndElaCpf(String eleCpf, String elaCpf);

    Optional<Casal> findCasalByParoquiaAtualId(UUID idParoquia);

    @Query(value = "SELECT " +
            "c1.apelido AS apelidoEle, c1.telefone AS telefoneEle, " +
            "c2.apelido AS apelidoEla, c2.telefone AS telefoneEla, " +
            "ca.data_primeira_etapa, " +
            "p.nome AS paroquiaNome, ca.is_active " +
            "FROM casal ca " +
            "JOIN conjuge c1 ON ca.ele_id = c1.id " +
            "JOIN conjuge c2 ON ca.ela_id = c2.id " +
            "JOIN paroquia p ON ca.paroquia_atual_id = p.id " +
            "where ca.data_primeira_etapa != ''" +
            "and ca.data_segunda_etapa = '' " +
            "and ca.data_terceira_etapa = '' " +
            "AND ca.paroquia_atual_id = :paroquiaAtualId", nativeQuery = true)
    List<Map<String, Object>> getCasaisPrimeiraEtapaPorParoquia(UUID paroquiaAtualId);

    @Query(value = "SELECT " +
            "c1.apelido AS apelidoEle, c1.telefone AS telefoneEle, " +
            "c2.apelido AS apelidoEla, c2.telefone AS telefoneEla, " +
            "ca.data_primeira_etapa, " +
            "p.nome AS paroquiaNome, ca.is_active " +
            "FROM casal ca " +
            "JOIN conjuge c1 ON ca.ele_id = c1.id " +
            "JOIN conjuge c2 ON ca.ela_id = c2.id " +
            "JOIN paroquia p ON ca.paroquia_atual_id = p.id " +
            "where ca.data_primeira_etapa != ''" +
            "and ca.data_segunda_etapa != '' " +
            "and ca.data_terceira_etapa = '' " +
            "AND ca.paroquia_atual_id = :paroquiaAtualId", nativeQuery = true)
    List<Map<String, Object>> getCasaisSegundaEtapaPorParoquia(UUID paroquiaAtualId);

    @Query(value = "SELECT " +
            "c1.apelido AS apelidoEle, c1.telefone AS telefoneEle," +
            "c2.apelido as apelidoEla, c2.telefone as telefoneEla," +
            "ca.data_casamento_civil as casamentoCivil," +
            "p.nome as paroquiaNome, ca.is_active " +
            "FROM casal ca " +
            "JOIN conjuge c1 on ca.ele_id = c1.id " +
            "JOIN conjuge c2 on ca.ela_id = c2.id " +
            "JOIN paroquia p on ca.paroquia_atual_id = p.id " +
            "where ca.data_casamento_religioso = '' " +
            "and ca.paroquia_atual_id = :paroquiaId;", nativeQuery = true)
    List<Map<String, Object>> getCasaisSemSacramentoDoMatrimonio(UUID paroquiaId);
}
