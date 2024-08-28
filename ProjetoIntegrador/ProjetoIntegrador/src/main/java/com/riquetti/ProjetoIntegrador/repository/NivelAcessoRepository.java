package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.NivelAcesso;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NivelAcessoRepository {

    private final JdbcTemplate jdbcTemplate;

    public NivelAcessoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public NivelAcesso findById(Long idNivelAcesso) {
        String sql = "SELECT * FROM public.nivel_acesso WHERE id_nivel_acesso = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idNivelAcesso}, new NivelAcessoRowMapper());
    }

    public List<NivelAcesso> findAll() {
        String sql = "SELECT * FROM public.nivel_acesso";
        return jdbcTemplate.query(sql, new NivelAcessoRowMapper());
    }

    public int save(NivelAcesso nivelAcesso) {
        String sql = "INSERT INTO public.nivel_acesso (nivel_acesso) VALUES (?)";
        return jdbcTemplate.update(sql, nivelAcesso.getNivelAcesso());
    }

    public int update(NivelAcesso nivelAcesso) {
        String sql = "UPDATE public.nivel_acesso SET nivel_acesso = ? WHERE id_nivel_acesso = ?";
        return jdbcTemplate.update(sql, nivelAcesso.getNivelAcesso(), nivelAcesso.getIdNivelAcesso());
    }

    public int delete(Long idNivelAcesso) {
        String sql = "DELETE FROM public.nivel_acesso WHERE id_nivel_acesso = ?";
        return jdbcTemplate.update(sql, idNivelAcesso);
    }

    private static class NivelAcessoRowMapper implements RowMapper<NivelAcesso> {
        @Override
        public NivelAcesso mapRow(ResultSet rs, int rowNum) throws SQLException {
            NivelAcesso nivelAcesso = new NivelAcesso();
            nivelAcesso.setIdNivelAcesso(rs.getLong("id_nivel_acesso"));
            nivelAcesso.setNivelAcesso(rs.getString("nivel_acesso"));
            return nivelAcesso;
        }
    }

}
