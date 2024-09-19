package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.ComercioRaioVias;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComercioRaioViasRepository {

    private final JdbcTemplate jdbcTemplate;

    public ComercioRaioViasRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
          localizacao,  -- Incluído aqui como string
          comprimento_total_ponderado,
          CASE 
            WHEN comprimento_total_ponderado > 15000 THEN 'Excelente'
            WHEN comprimento_total_ponderado BETWEEN 10000 AND 15000 THEN 'Boa'
            WHEN comprimento_total_ponderado BETWEEN 5000 AND 10000 THEN 'Média'
            WHEN comprimento_total_ponderado BETWEEN 0 AND 5000 THEN 'Ruim'
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
              localizacao,  -- Selecionando a localização em formato de string
              comprimento_total_ponderado,
              CASE 
                WHEN comprimento_total_ponderado > 15000 THEN 'Excelente'
                WHEN comprimento_total_ponderado BETWEEN 10000 AND 15000 THEN 'Boa'
                WHEN comprimento_total_ponderado BETWEEN 5000 AND 10000 THEN 'Média'
                WHEN comprimento_total_ponderado BETWEEN 0 AND 5000 THEN 'Ruim'
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
            entity.setLocalizacao(rs.getString("localizacao"));  // Mapeando a localização convertida
            entity.setComprimentoTotalPonderado(rs.getBigDecimal("comprimento_total_ponderado"));
            entity.setAcessibilidade(rs.getString("acessibilidade"));
            return entity;
        });
    }


    // calculo de acessibilidade para diferentes distancias
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
        WHEN comprimento_total_ponderado BETWEEN 0 AND 5000 THEN 'Ruim'
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

    public List<ComercioRaioVias> findAllViasDistancia() {
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
        WHEN comprimento_total_ponderado BETWEEN 0 AND 5000 THEN 'Ruim'
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
