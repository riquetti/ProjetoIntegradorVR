package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.ComercioRaioVias;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório responsável pela interação com a tabela `localizacao_comercios`
 * e pela execução de consultas relacionadas a comércios, suas localizações e
 * dados de vias do OSM(Open Street Map).
 * Os dados são utilizados para calcular o comprimento das vias da base.
 *
 * Comprimento Ponderado - utiliza o comprimento das vias, classificação (as classes representam a importância e velocidade)
 * e são atribuidos pesos diferentes para cada tipo de via.
 * 'motorway', 'trunk', 'primary', 'primary_link' - peso 4
 * 'secondary', 'secondary_link' - peso 3
 * 'tertiary', 'tertiary_link' - peso 2
 * outras tipos - peso 1
 * O resultado é divido por 10, obtem no calculo ponderado.
 *
 * Com isso o resultado é classificado em Muito Ruim (0 até 1000), Ruim(1000 até 5000),
 * Médio(5000 até 10000), Boa(10000 até 15000) e Excelente(> 15000).
 *
 * Comprimento - Cálculo considera o comprimento das vias somadas dentro do raio de ação.
 * No Banco de Dados foi utilizado a extensão postgis para análises espaciais.
 */
@Repository
public class ComercioRaioViasRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     *  Construtor da classe ComercioRaioViasRepository.
     *
     * @param jdbcTemplate o JdbcTemplate utilizado para executar as consultas.
     */
    public ComercioRaioViasRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Busca informações sobre o comércio com base no seu ID, incluindo a acessibilidade das vias próximas.
     *
     * Este método executa uma consulta SQL para recuperar dados do comércio, bem como calcular o comprimento total
     * ponderado das vias que estão dentro do raio de ação do comércio. As vias são ponderadas
     * com base em sua importância e classificadas.
     *
     * @param idComercio O ID do comércio a ser buscado. Deve ser um valor válido que exista na
     *                   tabela de localização de comércios.
     * @return Uma lista de ComercioRaioVias contendo as informações do comércio e a acessibilidade
     *          das vias próximas. Se não houver comércio correspondente ao ID fornecido, a lista retornada
     *          estará vazia.
     */
    public List<ComercioRaioVias> findByComercioId(Long idComercio) {
        String sql = """
        WITH vias_proximas AS (
          SELECT 
            comercio.id_comercio,
            comercio.nome,
            comercio.descricao,
            comercio.raio_acao_metros,
            ST_AsText(comercio.localizacao) AS localizacao,  -- Converte a localização para string
            SUM(
              CASE 
                WHEN vias.fclass IN ('motorway', 'trunk', 'primary', 'primary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 4 / 10
                WHEN vias.fclass IN ('secondary', 'secondary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 3 / 10
                WHEN vias.fclass IN ('tertiary', 'tertiary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 2 / 10
                ELSE ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 1 / 10
              END
            ) AS comprimento_total_ponderado
          FROM 
            public.localizacao_comercios AS comercio
          JOIN 
            public."Vias_Limeira_OSM" AS vias
          ON 
            ST_DWithin(vias.geom, comercio.localizacao::geometry, comercio.raio_acao_metros)
          WHERE 
            comercio.id_comercio = ?
            AND vias.fclass IN ('motorway', 'trunk', 'primary', 'primary_link', 'secondary', 'secondary_link', 'tertiary', 'tertiary_link', 'residential', 'living_street', 'unclassified')
          GROUP BY 
            comercio.id_comercio, comercio.nome, comercio.descricao, comercio.raio_acao_metros, comercio.localizacao
        )
        SELECT 
          id_comercio,
          nome,
          descricao,
          raio_acao_metros,
          localizacao,
          comprimento_total_ponderado,
          CASE 
            WHEN comprimento_total_ponderado > 15000 THEN 'Excelente'
            WHEN comprimento_total_ponderado BETWEEN 10000 AND 15000 THEN 'Boa'
            WHEN comprimento_total_ponderado BETWEEN 5000 AND 10000 THEN 'Média'
            WHEN comprimento_total_ponderado BETWEEN 1000 AND 5000 THEN 'Ruim'
            ELSE 'Muito Ruim'
          END AS acessibilidade
        FROM 
          vias_proximas;
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ComercioRaioVias entity = new ComercioRaioVias();
            entity.setIdComercio(rs.getLong("id_comercio"));
            entity.setNome(rs.getString("nome"));
            entity.setDescricao(rs.getString("descricao"));
            entity.setRaioAcaoMetros(rs.getLong("raio_acao_metros"));
            entity.setLocalizacao(rs.getString("localizacao"));  // Mapeia a localização como string
            entity.setComprimentoTotalPonderado(rs.getBigDecimal("comprimento_total_ponderado"));
            entity.setAcessibilidade(rs.getString("acessibilidade"));
            return entity;
        }, idComercio);
    }

    /**
     * Busca informações de todos os comércios e a acessibilidade das vias próximas.
     *
     * Este método executa uma consulta SQL para recuperar dados de todos os comércios na tabela de localização,
     * incluindo o comprimento total ponderado das vias que estão dentro do raio de ação de cada comércio.
     * As vias são classificadas e ponderadas com base em sua importância para calcular a acessibilidade.
     *
     * @return  Uma lista de {@link ComercioRaioVias} contendo as informações de todos os comércios, incluindo
     *          a acessibilidade das vias próximas. Se não houver comércios registrados, a lista retornada
     *          estará vazia.
     */
    public List<ComercioRaioVias> findAll() {
        String sql = """
            WITH vias_proximas AS (
              SELECT 
                comercio.id_comercio,
                comercio.nome,
                comercio.descricao,
                comercio.raio_acao_metros,
                ST_AsText(comercio.localizacao) AS localizacao,  -- Convertendo a localização para texto
                SUM(
                  CASE 
                    WHEN vias.fclass IN ('motorway', 'trunk', 'primary', 'primary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 4 / 10
                    WHEN vias.fclass IN ('secondary', 'secondary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 3 / 10
                    WHEN vias.fclass IN ('tertiary', 'tertiary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 2 / 10
                    ELSE ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 1 / 10
                  END
                ) AS comprimento_total_ponderado
              FROM 
                public.localizacao_comercios AS comercio
              JOIN 
                public."Vias_Limeira_OSM" AS vias
              ON 
                ST_DWithin(vias.geom, comercio.localizacao::geometry, comercio.raio_acao_metros)  
              WHERE 
                vias.fclass IN ('motorway', 'trunk', 'primary', 'primary_link', 'secondary', 'secondary_link', 'tertiary', 'tertiary_link', 'residential', 'living_street', 'unclassified')
              GROUP BY 
                comercio.id_comercio, comercio.nome, comercio.descricao, comercio.raio_acao_metros, comercio.localizacao  
            )
            SELECT 
              id_comercio,
              nome,
              descricao,
              raio_acao_metros,
              localizacao,
              comprimento_total_ponderado,
              CASE 
                WHEN comprimento_total_ponderado > 15000 THEN 'Excelente'
                WHEN comprimento_total_ponderado BETWEEN 10000 AND 15000 THEN 'Boa'
                WHEN comprimento_total_ponderado BETWEEN 5000 AND 10000 THEN 'Média'
                WHEN comprimento_total_ponderado BETWEEN 1000 AND 5000 THEN 'Ruim'
                ELSE 'Muito Ruim'
              END AS acessibilidade
            FROM 
              vias_proximas;
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ComercioRaioVias entity = new ComercioRaioVias();
            entity.setIdComercio(rs.getLong("id_comercio"));
            entity.setNome(rs.getString("nome"));
            entity.setDescricao(rs.getString("descricao"));
            entity.setRaioAcaoMetros(rs.getLong("raio_acao_metros"));
            entity.setLocalizacao(rs.getString("localizacao"));
            entity.setComprimentoTotalPonderado(rs.getBigDecimal("comprimento_total_ponderado"));
            entity.setAcessibilidade(rs.getString("acessibilidade"));
            return entity;
        });
    }


    /**
     * Busca informações sobre um comércio específico e a acessibilidade das vias em diferentes distâncias.
     *
     * Este método executa uma consulta SQL para recuperar dados de um comércio identificado pelo seu ID.
     * Ele calcula o comprimento total ponderado das vias próximas com base em sua classificação,
     * além de medir o comprimento das vias dentro de intervalos de 500, 1000, 1500, 2000 e 2500 metros.
     *
     * @param idComercio O ID do comércio a ser pesquisado.
     * @return Uma lista de ComercioRaioVias contendo as informações do comércio e a acessibilidade
     *         das vias em várias distâncias. Se o comércio não for encontrado, a lista retornada estará vazia.
     */
    public List<ComercioRaioVias> findByComercioIdDistancia(Long idComercio) {
        String sql = """
    WITH vias_proximas AS (
      SELECT 
        comercio.id_comercio,
        comercio.nome,
        comercio.descricao,
        comercio.raio_acao_metros,
        ST_AsText(comercio.localizacao) AS localizacao,  
        SUM(
          CASE 
            WHEN vias.fclass IN ('motorway', 'trunk', 'primary', 'primary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 4 / 10
            WHEN vias.fclass IN ('secondary', 'secondary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 3 / 10
            WHEN vias.fclass IN ('tertiary', 'tertiary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 2 / 10
            ELSE ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 1 / 10
          END
        ) AS comprimento_total_ponderado,
        SUM(
          CASE 
            WHEN ST_DWithin(vias.geom, comercio.localizacao::geometry, 500) THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, 500)))
            ELSE 0
          END
        ) AS comprimento500,
        SUM(
          CASE 
            WHEN ST_DWithin(vias.geom, comercio.localizacao::geometry, 1000) THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, 1000)))
            ELSE 0
          END
        ) AS comprimento1000,
        SUM(
          CASE 
            WHEN ST_DWithin(vias.geom, comercio.localizacao::geometry, 1500) THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, 1500)))
            ELSE 0
          END
        ) AS comprimento1500,
        SUM(
          CASE 
            WHEN ST_DWithin(vias.geom, comercio.localizacao::geometry, 2000) THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, 2000)))
            ELSE 0
          END
        ) AS comprimento2000,
        SUM(
          CASE 
            WHEN ST_DWithin(vias.geom, comercio.localizacao::geometry, 2500) THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, 2500)))
            ELSE 0
          END
        ) AS comprimento2500
      FROM 
        public.localizacao_comercios AS comercio
      JOIN 
        public."Vias_Limeira_OSM" AS vias
      ON 
        ST_DWithin(vias.geom, comercio.localizacao::geometry, comercio.raio_acao_metros)  
      WHERE 
        comercio.id_comercio = ?
        AND vias.fclass IN ('motorway', 'trunk', 'primary', 'primary_link', 'secondary', 'secondary_link', 'tertiary', 'tertiary_link', 'residential', 'living_street', 'unclassified')
      GROUP BY 
        comercio.id_comercio, comercio.nome, comercio.descricao, comercio.raio_acao_metros, comercio.localizacao  
    )
    SELECT 
      id_comercio,
      nome,
      descricao,
      raio_acao_metros,
      localizacao,  
      comprimento_total_ponderado,
      comprimento500,
      comprimento1000,
      comprimento1500,
      comprimento2000,
      comprimento2500,
      CASE 
        WHEN comprimento_total_ponderado > 15000 THEN 'Excelente'
        WHEN comprimento_total_ponderado BETWEEN 10000 AND 15000 THEN 'Boa'
        WHEN comprimento_total_ponderado BETWEEN 5000 AND 10000 THEN 'Média'
        WHEN comprimento_total_ponderado BETWEEN 1000 AND 5000 THEN 'Ruim'
        ELSE 'Muito Ruim'
      END AS acessibilidade
    FROM 
      vias_proximas;
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ComercioRaioVias entity = new ComercioRaioVias();
            entity.setIdComercio(rs.getLong("id_comercio"));
            entity.setNome(rs.getString("nome"));
            entity.setDescricao(rs.getString("descricao"));
            entity.setRaioAcaoMetros(rs.getLong("raio_acao_metros"));
            entity.setLocalizacao(rs.getString("localizacao"));
            entity.setComprimentoTotalPonderado(rs.getBigDecimal("comprimento_total_ponderado"));
            entity.setComprimento500(rs.getBigDecimal("comprimento500"));
            entity.setComprimento1000(rs.getBigDecimal("comprimento1000"));
            entity.setComprimento1500(rs.getBigDecimal("comprimento1500"));
            entity.setComprimento2000(rs.getBigDecimal("comprimento2000"));
            entity.setComprimento2500(rs.getBigDecimal("comprimento2500"));
            entity.setAcessibilidade(rs.getString("acessibilidade"));
            return entity;
        }, idComercio);
    }

    /**
     * Busca informações sobre todos os comércios e a acessibilidade das vias em diferentes distâncias.
     *
     * Este método executa uma consulta SQL que recupera dados de todos os comércios, calculando
     * o comprimento total ponderado das vias próximas com base em suas classificações. Além disso,
     * mede o comprimento das vias dentro de intervalos de 500, 1000, 1500, 2000 e 2500 metros.
     *
     * A acessibilidade é classificada como:
     * - 'Excelente' para comprimento total ponderado acima de 15000 metros
     * - 'Boa' para comprimento entre 10000 e 15000 metros
     * - 'Média' para comprimento entre 5000 e 10000 metros
     * - 'Ruim' para comprimento entre 0 e 5000 metros
     * - 'Muito Ruim' para qualquer outro caso.
     *
     * @return Uma lista de {@link ComercioRaioVias} contendo as informações de todos os comércios
     *         e a acessibilidade das vias em várias distâncias. Se não houver comércios, a lista retornada estará vazia.
     */
    public List<ComercioRaioVias> findAllViasDistancia() {
        String sql = """
    WITH vias_proximas AS (
      SELECT 
        comercio.id_comercio,
        comercio.nome,
        comercio.descricao,
        comercio.raio_acao_metros,
        ST_AsText(comercio.localizacao) AS localizacao,  -- Convertendo a localização para texto
        SUM(
          CASE 
            WHEN vias.fclass IN ('motorway', 'trunk', 'primary', 'primary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 4 / 10
            WHEN vias.fclass IN ('secondary', 'secondary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 3 / 10
            WHEN vias.fclass IN ('tertiary', 'tertiary_link') THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 2 / 10
            ELSE ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, comercio.raio_acao_metros))) * 1 / 10
          END
        ) AS comprimento_total_ponderado,
        SUM(
          CASE 
            WHEN ST_DWithin(vias.geom, comercio.localizacao::geometry, 500) THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, 500)))
            ELSE 0
          END
        ) AS comprimento500,
        SUM(
          CASE 
            WHEN ST_DWithin(vias.geom, comercio.localizacao::geometry, 1000) THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, 1000)))
            ELSE 0
          END
        ) AS comprimento1000,
        SUM(
          CASE 
            WHEN ST_DWithin(vias.geom, comercio.localizacao::geometry, 1500) THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, 1500)))
            ELSE 0
          END
        ) AS comprimento1500,
        SUM(
          CASE 
            WHEN ST_DWithin(vias.geom, comercio.localizacao::geometry, 2000) THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, 2000)))
            ELSE 0
          END
        ) AS comprimento2000,
        SUM(
          CASE 
            WHEN ST_DWithin(vias.geom, comercio.localizacao::geometry, 2500) THEN ST_Length(ST_Intersection(vias.geom::geography, ST_Buffer(comercio.localizacao::geography, 2500)))
            ELSE 0
          END
        ) AS comprimento2500
      FROM 
        public.localizacao_comercios AS comercio
      JOIN 
        public."Vias_Limeira_OSM" AS vias
      ON 
        ST_DWithin(vias.geom, comercio.localizacao::geometry, comercio.raio_acao_metros)  
      WHERE 
        vias.fclass IN ('motorway', 'trunk', 'primary', 'primary_link', 'secondary', 'secondary_link', 'tertiary', 'tertiary_link', 'residential', 'living_street', 'unclassified')
      GROUP BY 
        comercio.id_comercio, comercio.nome, comercio.descricao, comercio.raio_acao_metros, comercio.localizacao  
    )
    SELECT 
      id_comercio,
      nome,
      descricao,
      raio_acao_metros,
      localizacao,  
      comprimento_total_ponderado,
      comprimento500,
      comprimento1000,
      comprimento1500,
      comprimento2000,
      comprimento2500,
      CASE 
        WHEN comprimento_total_ponderado > 15000 THEN 'Excelente'
        WHEN comprimento_total_ponderado BETWEEN 10000 AND 15000 THEN 'Boa'
        WHEN comprimento_total_ponderado BETWEEN 5000 AND 10000 THEN 'Média'
        WHEN comprimento_total_ponderado BETWEEN 1000 AND 5000 THEN 'Ruim'
        ELSE 'Muito Ruim'
      END AS acessibilidade
    FROM 
      vias_proximas;
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ComercioRaioVias entity = new ComercioRaioVias();
            entity.setIdComercio(rs.getLong("id_comercio"));
            entity.setNome(rs.getString("nome"));
            entity.setDescricao(rs.getString("descricao"));
            entity.setRaioAcaoMetros(rs.getLong("raio_acao_metros"));
            entity.setLocalizacao(rs.getString("localizacao"));
            entity.setComprimentoTotalPonderado(rs.getBigDecimal("comprimento_total_ponderado"));
            entity.setComprimento500(rs.getBigDecimal("comprimento500"));
            entity.setComprimento1000(rs.getBigDecimal("comprimento1000"));
            entity.setComprimento1500(rs.getBigDecimal("comprimento1500"));
            entity.setComprimento2000(rs.getBigDecimal("comprimento2000"));
            entity.setComprimento2500(rs.getBigDecimal("comprimento2500"));
            entity.setAcessibilidade(rs.getString("acessibilidade"));
            return entity;
        });
    }


}
