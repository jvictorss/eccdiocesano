package br.com.verbum.eccdiocesano.domain.repository;

import br.com.verbum.eccdiocesano.domain.entities.Casal;
import br.com.verbum.eccdiocesano.domain.reuse.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CasalRepository extends BaseRepository<Casal> {

    // procurar todos os casais da paróquia que estão ativos

    // query escrita para procurar casais da paróquia entre ativos e inativos;

    // query escrita para procurar casais que foram transferidos;

    Optional<Casal> findCasalByEleTelefoneAndElaTelefone(String telefoneEle, String telefoneEla);

    @Query(value =
            """
             SELECT
             c1.apelido AS apelidoEle, c1.telefone AS telefoneEle,
             c2.apelido AS apelidoEla, c2.telefone AS telefoneEla,
             ca.data_primeira_etapa,
             ca.is_active
             FROM casal ca
             JOIN conjuge c1 ON ca.ele_id = c1.id
             JOIN conjuge c2 ON ca.ela_id = c2.id
             where ca.data_primeira_etapa != ''
             and ca.data_segunda_etapa = ''
             and ca.data_terceira_etapa = ''
             order by apelidoEle;
            """, nativeQuery = true)
    List<Map<String, Object>> getCasaisPrimeiraEtapa();

    @Query(value =
            """
             SELECT
             count(1) as count_primeira_etapa
             FROM casal ca
             JOIN conjuge c1 ON ca.ele_id = c1.id
             JOIN conjuge c2 ON ca.ela_id = c2.id
             where ca.data_primeira_etapa != ''
             and ca.data_segunda_etapa = ''
             and ca.data_terceira_etapa = ''
            """, nativeQuery = true)
    Map<String, Object> countCasaisPrimeiraEtapa();

    @Query(value =
            """
            SELECT
            c1.apelido AS apelidoEle, c1.telefone AS telefoneEle,
            c2.apelido AS apelidoEla, c2.telefone AS telefoneEla,
            ca.data_primeira_etapa,
            ca.data_segunda_etapa,
            ca.is_active
            FROM casal ca
            JOIN conjuge c1 ON ca.ele_id = c1.id
            JOIN conjuge c2 ON ca.ela_id = c2.id
            where ca.data_primeira_etapa != ''
            and ca.data_segunda_etapa != ''
            and ca.data_terceira_etapa = ''
            order by apelidoEle;
            """, nativeQuery = true)
    List<Map<String, Object>> getCasaisSegundaEtapa();

    @Query(value =
            """
            SELECT
            count(1) as count_segunda_etapa
            FROM casal ca
            JOIN conjuge c1 ON ca.ele_id = c1.id
            JOIN conjuge c2 ON ca.ela_id = c2.id
            where ca.data_primeira_etapa != ''
            and ca.data_segunda_etapa != ''
            and ca.data_terceira_etapa = ''
            """, nativeQuery = true)
    Map<String, Object> countCasaisSegundaEtapa();

    @Query(value =
            """
            SELECT
            c1.apelido AS apelidoEle, c1.telefone AS telefoneEle,
            c2.apelido AS apelidoEla, c2.telefone AS telefoneEla,
            ca.data_primeira_etapa,
            ca.data_segunda_etapa,
            ca.data_terceira_etapa,
            ca.is_active
            FROM casal ca
            JOIN conjuge c1 ON ca.ele_id = c1.id
            JOIN conjuge c2 ON ca.ela_id = c2.id
            where ca.data_primeira_etapa != ''
            and ca.data_segunda_etapa != ''
            and ca.data_terceira_etapa != ''
            order by apelidoEle;
            """

            , nativeQuery = true)
    List<Map<String, Object>> getCasaisTerceiraEtapa();

    @Query(value =
            """
            SELECT
            count(1) as count_terceira_etapa
            FROM casal ca
            JOIN conjuge c1 ON ca.ele_id = c1.id
            JOIN conjuge c2 ON ca.ela_id = c2.id
            where ca.data_primeira_etapa != ''
            and ca.data_segunda_etapa != ''
            and ca.data_terceira_etapa != '';
            """
            , nativeQuery = true)
    Map<String, Object> countCasaisTerceiraEtapa();

    @Query(value =
        """
        SELECT
            c1.apelido AS apelidoEle, c1.telefone AS telefoneEle,
            c2.apelido AS apelidoEla, c2.telefone AS telefoneEla,
            ca.data_casamento_civil AS casamentoCivil,
            ca.is_active
        FROM casal ca
        JOIN conjuge c1 ON ca.ele_id = c1.id
        JOIN conjuge c2 ON ca.ela_id = c2.id
        where ca.data_casamento_religioso = ''
        order by apelidoEle;
        """, nativeQuery = true)
    List<Map<String, Object>> getCasaisSemSacramentoDoMatrimonio();

    @Query(value =
            """
            SELECT
                count(1) as count_sem_sacramento
            FROM casal ca
            JOIN conjuge c1 ON ca.ele_id = c1.id
            JOIN conjuge c2 ON ca.ela_id = c2.id
            where ca.data_casamento_religioso = '';
            """, nativeQuery = true)
    Map<String, Object> countCasaisSemSacramentoDoMatrimonio();

    @Query(value =
        """ 
        SELECT
            c1.apelido AS apelidoEle, c1.telefone AS telefoneEle,
            c2.apelido AS apelidoEla, c2.telefone AS telefoneEla,
            ca.data_primeira_etapa,
            ca.data_segunda_etapa,
            ca.data_terceira_etapa,
            ca.is_active 
        FROM casal ca
        JOIN conjuge c1 ON ca.ele_id = c1.id
        JOIN conjuge c2 ON ca.ela_id = c2.id
        where ca.data_primeira_etapa != ''
        AND ca.is_active = FALSE
        ORDER BY apelidoEle;
        """, nativeQuery = true)
    List<Map<String, Object>> getInactiveCouples();

    @Query(value =
            """ 
            SELECT
                count(1) as total_inactive_couples
            FROM casal ca
            JOIN conjuge c1 ON ca.ele_id = c1.id
            JOIN conjuge c2 ON ca.ela_id = c2.id
            where ca.data_primeira_etapa != ''
            AND ca.is_active = FALSE;
            """, nativeQuery = true)
    Map<String, Object> countInactiveCouples();

    @Query(value =
            """ 
            SELECT
                c1.apelido AS apelidoEle, c1.telefone AS telefoneEle,
                c2.apelido AS apelidoEla, c2.telefone AS telefoneEla,
                ca.data_primeira_etapa,
                ca.data_segunda_etapa,
                ca.data_terceira_etapa,
                ca.is_active 
            FROM casal ca
            JOIN conjuge c1 ON ca.ele_id = c1.id
            JOIN conjuge c2 ON ca.ela_id = c2.id
            where ca.data_primeira_etapa != ''
            AND ca.is_active = true
            order by apelidoEle;
            """, nativeQuery = true)
    List<Map<String, Object>> getActiveCouples();

    @Query(value =
    """
    SELECT
        count(CASE WHEN ca.data_primeira_etapa != '' THEN 1 END) AS count_primeira_etapa,
        count(CASE WHEN ca.data_segunda_etapa != '' THEN 1 END) AS count_segunda_etapa,
        count(CASE WHEN ca.data_terceira_etapa != '' THEN 1 END) AS count_terceira_etapa,
        count(1) as total_active_couples
    FROM casal ca
    JOIN conjuge c1 ON ca.ele_id = c1.id
    JOIN conjuge c2 ON ca.ela_id = c2.id
    WHERE ca.is_active = true;
    """, nativeQuery = true)
    Map<String, Object> countActiveCouples();

}
