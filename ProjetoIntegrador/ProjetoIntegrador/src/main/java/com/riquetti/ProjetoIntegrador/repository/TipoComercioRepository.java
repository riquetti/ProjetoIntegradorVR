package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.TipoComercio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TipoComercioRepository {

    private final JdbcTemplate jdbcTemplate;

    public TipoComercioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TipoComercio findById(Long idTipoComercio) {
        String sql = "SELECT * FROM public.tipo_comercio WHERE id_tipo_comercio = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idTipoComercio}, new TipoComercioRowMapper());
    }

    public List<TipoComercio> findAll() {
        String sql = "SELECT * FROM public.tipo_comercio";
        return jdbcTemplate.query(sql, new TipoComercioRowMapper());
    }

    public int save(TipoComercio tipoComercio) {
        String sql = "INSERT INTO public.tipo_comercio (descricao) VALUES (?)";
        return jdbcTemplate.update(sql, tipoComercio.getDescricao());
    }

    public int update(TipoComercio tipoComercio) {
        String sql = "UPDATE public.tipo_comercio SET descricao = ? WHERE id_tipo_comercio = ?";
        return jdbcTemplate.update(sql, tipoComercio.getDescricao(), tipoComercio.getIdTipoComercio());
    }

    public int delete(Long idTipoComercio) {
        String sql = "DELETE FROM public.tipo_comercio WHERE id_tipo_comercio = ?";
        return jdbcTemplate.update(sql, idTipoComercio);
    }

    private static class TipoComercioRowMapper implements RowMapper<TipoComercio> {
        @Override
        public TipoComercio mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoComercio tipoComercio = new TipoComercio();
            tipoComercio.setIdTipoComercio(rs.getLong("id_tipo_comercio"));
            tipoComercio.setDescricao(rs.getString("descricao"));
            return tipoComercio;
        }
    }

}
