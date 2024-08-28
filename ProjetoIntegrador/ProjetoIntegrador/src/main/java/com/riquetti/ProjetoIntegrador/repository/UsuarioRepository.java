package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Usuario findById(Integer id) {
        String sql = "SELECT * FROM public.usuarios WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UsuarioRowMapper());
    }

    public List<Usuario> findAll() {
        String sql = "SELECT * FROM public.usuarios";
        return jdbcTemplate.query(sql, new UsuarioRowMapper());
    }

    public int save(Usuario usuario) {
        String sql = "INSERT INTO public.usuarios (nome, email, senha, id_nivel_acesso, ativo) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getIdNivelAcesso(), usuario.getAtivo());
    }

    public int update(Usuario usuario) {
        String sql = "UPDATE public.usuarios SET nome = ?, email = ?, senha = ?, id_nivel_acesso = ?, ativo = ? WHERE id = ?";
        return jdbcTemplate.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getIdNivelAcesso(), usuario.getAtivo(), usuario.getId());
    }

    public int delete(Integer id) {
        String sql = "DELETE FROM public.usuarios WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private static class UsuarioRowMapper implements RowMapper<Usuario> {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setIdNivelAcesso(rs.getInt("id_nivel_acesso"));
            usuario.setAtivo(rs.getBoolean("ativo"));
            return usuario;
        }
    }

}
