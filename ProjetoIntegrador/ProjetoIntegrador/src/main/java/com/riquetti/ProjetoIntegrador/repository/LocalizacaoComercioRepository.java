package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.LocalizacaoComercio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocalizacaoComercioRepository {

    private final JdbcTemplate jdbcTemplate;

    public LocalizacaoComercioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public LocalizacaoComercio findById(Long idComercio) {
        String sql = "SELECT id_comercio, nome, descricao, id_tipo_comercio, raio_acao_metros, " +
                "ST_AsText(localizacao) AS localizacao_texto " +
                "FROM public.localizacao_comercios WHERE id_comercio = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{idComercio}, new LocalizacaoComercioRowMapper());
    }

    public List<LocalizacaoComercio> findAll() {
        String sql = "SELECT id_comercio, nome, descricao, id_tipo_comercio, raio_acao_metros, " +
                "ST_AsText(localizacao) AS localizacao_texto " +
                "FROM public.localizacao_comercios";
        return jdbcTemplate.query(sql, new LocalizacaoComercioRowMapper());
    }

    public int save(LocalizacaoComercio comercio) {
        String sql = "INSERT INTO public.localizacao_comercios (nome, descricao, id_tipo_comercio, raio_acao_metros, localizacao) " +
                "VALUES (?, ?, ?, ?, ST_GeomFromText(?, 4326))";
        return jdbcTemplate.update(sql, comercio.getNome(), comercio.getDescricao(),
                comercio.getIdTipoComercio(), comercio.getRaioAcaoMetros(), comercio.getLocalizacao().toString());
    }

    public int update(LocalizacaoComercio comercio) {
        String sql = "UPDATE public.localizacao_comercios SET nome = ?, descricao = ?, id_tipo_comercio = ?, " +
                "raio_acao_metros = ?, localizacao = ST_GeomFromText(?, 4326) " +
                "WHERE id_comercio = ?";
        return jdbcTemplate.update(sql, comercio.getNome(), comercio.getDescricao(), comercio.getIdTipoComercio(),
                comercio.getRaioAcaoMetros(), comercio.getLocalizacao().toString(), comercio.getIdComercio());
    }

    public int delete(Long idComercio) {
        String sql = "DELETE FROM public.localizacao_comercios WHERE id_comercio = ?";
        return jdbcTemplate.update(sql, idComercio);
    }

    private static class LocalizacaoComercioRowMapper implements RowMapper<LocalizacaoComercio> {
        @Override
        public LocalizacaoComercio mapRow(ResultSet rs, int rowNum) throws SQLException {
            LocalizacaoComercio comercio = new LocalizacaoComercio();
            comercio.setIdComercio(rs.getLong("id_comercio"));
            comercio.setNome(rs.getString("nome"));
            comercio.setDescricao(rs.getString("descricao"));
            comercio.setIdTipoComercio(rs.getLong("id_tipo_comercio"));
            comercio.setRaioAcaoMetros(rs.getBigDecimal("raio_acao_metros"));

            // Mapeando a localização como String (WKT)
            comercio.setLocalizacao(rs.getString("localizacao_texto"));

            return comercio;
        }
    }

}
