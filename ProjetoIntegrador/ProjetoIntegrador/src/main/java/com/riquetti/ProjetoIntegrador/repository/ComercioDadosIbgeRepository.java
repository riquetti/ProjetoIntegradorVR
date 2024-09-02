package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.ComercioDadosIbge;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComercioDadosIbgeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ComercioDadosIbgeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Método existente para buscar por ID de comércio
    public List<ComercioDadosIbge> findByIdComercio(Long idComercio) {
        String sql =
                "SELECT " +
                        "    loc.id_comercio, " +
                        "    loc.nome, " +
                        "    ST_AsText(loc.localizacao) AS localizacao_texto, " +
                        "    loc.raio_acao_metros, " +
                        "    ROUND(AVG(basico.\"V005\")::numeric, 2) AS Renda_Media_IBGE2010 " +
                        "FROM " +
                        "    \"localizacao_comercios\" AS loc " +
                        "JOIN " +
                        "    \"IBGE_Limeira_2010\" AS ibge " +
                        "ON " +
                        "    ST_Intersects(" +
                        "        ST_Buffer(loc.localizacao::geography, loc.raio_acao_metros)::geometry, " +
                        "        ibge.geom::geometry" +
                        "    ) " +
                        "JOIN " +
                        "    \"Basico_Limeira_IBGE2010\" AS basico " +
                        "ON " +
                        "    ibge.cd_geocodi = basico.\"Cod_setor\" " +
                        "WHERE " +
                        "    loc.id_comercio = ? " +
                        "GROUP BY " +
                        "    loc.id_comercio, " +
                        "    loc.nome, " +
                        "    loc.localizacao, " +
                        "    loc.raio_acao_metros";

        return jdbcTemplate.query(sql, new Object[]{idComercio}, (rs, rowNum) -> {
            ComercioDadosIbge comercio = new ComercioDadosIbge();
            comercio.setIdComercio(rs.getLong("id_comercio"));
            comercio.setNome(rs.getString("nome"));
            comercio.setLocalizacaoTexto(rs.getString("localizacao_texto"));
            comercio.setRaioAcaoMetros(rs.getLong("raio_acao_metros"));
            comercio.setRendaMediaIbge2010(rs.getBigDecimal("Renda_Media_IBGE2010"));
            return comercio;
        });
    }

    // Novo método que aceita o raio de ação como parâmetro
    public List<ComercioDadosIbge> findByIdComercioWithRaio(Long idComercio, double raioAcaoMetros) {
        String sql =
                "SELECT " +
                        "    loc.id_comercio, " +
                        "    loc.nome, " +
                        "    ST_AsText(loc.localizacao) AS localizacao_texto, " +
                        "    ? AS raio_acao_metros, " + // Usando o valor passado como raio de ação
                        "    ROUND(AVG(basico.\"V005\")::numeric, 2) AS Renda_Media_IBGE2010 " +
                        "FROM " +
                        "    \"localizacao_comercios\" AS loc " +
                        "JOIN " +
                        "    \"IBGE_Limeira_2010\" AS ibge " +
                        "ON " +
                        "    ST_Intersects(" +
                        "        ST_Buffer(loc.localizacao::geography, ?)::geometry, " + // Buffer com o raio passado
                        "        ibge.geom::geometry" +
                        "    ) " +
                        "JOIN " +
                        "    \"Basico_Limeira_IBGE2010\" AS basico " +
                        "ON " +
                        "    ibge.cd_geocodi = basico.\"Cod_setor\" " +
                        "WHERE " +
                        "    loc.id_comercio = ? " +
                        "GROUP BY " +
                        "    loc.id_comercio, " +
                        "    loc.nome, " +
                        "    loc.localizacao";

        return jdbcTemplate.query(sql, new Object[]{raioAcaoMetros, raioAcaoMetros, idComercio}, (rs, rowNum) -> {
            ComercioDadosIbge comercio = new ComercioDadosIbge();
            comercio.setIdComercio(rs.getLong("id_comercio"));
            comercio.setNome(rs.getString("nome"));
            comercio.setLocalizacaoTexto(rs.getString("localizacao_texto"));
            comercio.setRaioAcaoMetros(rs.getLong("raio_acao_metros"));
            comercio.setRendaMediaIbge2010(rs.getBigDecimal("Renda_Media_IBGE2010"));
            return comercio;
        });
    }

    // Método findAll original
    public List<ComercioDadosIbge> findAll() {
        String sql =
                "SELECT \n" +
                        "    loc.id_comercio,\n" +
                        "    loc.nome,\n" +
                        "    ST_AsText(loc.localizacao) AS localizacao_texto,\n" +
                        "    loc.raio_acao_metros,\n" +
                        "    ROUND(AVG(basico.\"V005\")::numeric, 2) AS Renda_Media_IBGE2010\n" +
                        "FROM \n" +
                        "    \"localizacao_comercios\" AS loc\n" +
                        "JOIN \n" +
                        "    \"IBGE_Limeira_2010\" AS ibge\n" +
                        "ON \n" +
                        "    ST_Intersects(\n" +
                        "        ST_Buffer(loc.localizacao::geography, loc.raio_acao_metros)::geometry, \n" +
                        "        ibge.geom::geometry\n" +
                        "    )\n" +
                        "JOIN \n" +
                        "    \"Basico_Limeira_IBGE2010\" AS basico\n" +
                        "ON \n" +
                        "    ibge.cd_geocodi = basico.\"Cod_setor\"\n" +
                        "GROUP BY \n" +
                        "    loc.id_comercio, \n" +
                        "    loc.nome, \n" +
                        "    loc.localizacao, \n" +
                        "    loc.raio_acao_metros;";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ComercioDadosIbge loc = new ComercioDadosIbge();
            loc.setIdComercio(rs.getLong("id_comercio"));
            loc.setNome(rs.getString("nome"));
            loc.setLocalizacaoTexto(rs.getString("localizacao_texto"));
            loc.setRaioAcaoMetros(rs.getLong("raio_acao_metros"));
            loc.setRendaMediaIbge2010(rs.getBigDecimal("Renda_Media_IBGE2010"));
            return loc;
        });
    }

    // Novo método findAll que aceita o raio de ação como parâmetro
    public List<ComercioDadosIbge> findAllWithRaio(double raioAcaoMetros) {
        String sql =
                "SELECT \n" +
                        "    loc.id_comercio,\n" +
                        "    loc.nome,\n" +
                        "    ST_AsText(loc.localizacao) AS localizacao_texto,\n" +
                        "    ? AS raio_acao_metros,\n" +
                        "    ROUND(AVG(basico.\"V005\")::numeric, 2) AS Renda_Media_IBGE2010\n" +
                        "FROM \n" +
                        "    \"localizacao_comercios\" AS loc\n" +
                        "JOIN \n" +
                        "    \"IBGE_Limeira_2010\" AS ibge\n" +
                        "ON \n" +
                        "    ST_Intersects(\n" +
                        "        ST_Buffer(loc.localizacao::geography, ?)::geometry, \n" +
                        "        ibge.geom::geometry\n" +
                        "    )\n" +
                        "JOIN \n" +
                        "    \"Basico_Limeira_IBGE2010\" AS basico\n" +
                        "ON \n" +
                        "    ibge.cd_geocodi = basico.\"Cod_setor\"\n" +
                        "GROUP BY \n" +
                        "    loc.id_comercio, \n" +
                        "    loc.nome, \n" +
                        "    loc.localizacao;";

        return jdbcTemplate.query(sql, new Object[]{raioAcaoMetros, raioAcaoMetros}, (rs, rowNum) -> {
            ComercioDadosIbge loc = new ComercioDadosIbge();
            loc.setIdComercio(rs.getLong("id_comercio"));
            loc.setNome(rs.getString("nome"));
            loc.setLocalizacaoTexto(rs.getString("localizacao_texto"));
            loc.setRaioAcaoMetros(rs.getLong("raio_acao_metros"));
            loc.setRendaMediaIbge2010(rs.getBigDecimal("Renda_Media_IBGE2010"));
            return loc;
        });
    }

    // Método de consulta passando a localização e raio de ação
    public List<ComercioDadosIbge> findByLocationAndRaio(String pontoTexto, Long raioAcaoMetros) {
        String sql =
                "SELECT \n" +
                        "    ST_AsText(ST_SetSRID(ST_GeomFromText(?), 4326)) AS localizacao_texto, \n" +
                        "    ? AS raio_acao_metros,\n" +
                        "    ROUND(AVG(basico.\"V005\")::numeric, 2) AS Renda_Media_IBGE2010\n" +
                        "FROM \n" +
                        "    \"IBGE_Limeira_2010\" AS ibge\n" +
                        "JOIN \n" +
                        "    \"Basico_Limeira_IBGE2010\" AS basico\n" +
                        "ON \n" +
                        "    ibge.cd_geocodi = basico.\"Cod_setor\"\n" +
                        "WHERE \n" +
                        "    ST_Intersects(\n" +
                        "        ST_Buffer(ST_SetSRID(ST_GeomFromText(?), 4326)::geography, ?)::geometry, \n" +
                        "        ibge.geom::geometry\n" +
                        "    )\n" +
                        "GROUP BY \n" +
                        "    localizacao_texto, \n" +
                        "    raio_acao_metros;";

        return jdbcTemplate.query(sql, new Object[]{pontoTexto, raioAcaoMetros, pontoTexto, raioAcaoMetros}, (rs, rowNum) -> {
            ComercioDadosIbge loc = new ComercioDadosIbge();

            // Definindo valores padrão caso sejam nulos
            loc.setIdComercio(0L); // Valor padrão para idComercio
            loc.setNome("Ponto de consulta"); // Valor padrão para nome

            loc.setLocalizacaoTexto(rs.getString("localizacao_texto"));
            loc.setRaioAcaoMetros(rs.getLong("raio_acao_metros"));
            loc.setRendaMediaIbge2010(rs.getBigDecimal("Renda_Media_IBGE2010"));

            return loc;
        });
    }

}
