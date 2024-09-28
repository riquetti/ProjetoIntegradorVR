package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repositório para gerenciar o acesso a dados relacionados aos usuários.
 * Esta classe utiliza JdbcTemplate para executar operações de banco de dados.
 */
@Repository
public class UsuarioRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Construtor que inicializa o repositório com o JdbcTemplate.
     *
     * @param jdbcTemplate Instância do JdbcTemplate para operações de banco de dados.
     */
    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recupera um usuário pelo ID fornecido.
     *
     * @param id O ID do usuário a ser recuperado.
     * @return Um objeto Usuario correspondente ao ID especificado.
     */
    public Usuario findById(Integer id) {
        String sql =
                "SELECT * FROM public.usuarios WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UsuarioRowMapper());
    }

    /**
     * Recupera todos os usuários no banco de dados.
     *
     * @return Uma lista de objetos Usuario contendo todos os registros na tabela usuarios.
     */
    public List<Usuario> findAll() {
        String sql =
                "SELECT * FROM public.usuarios";
        return jdbcTemplate.query(sql, new UsuarioRowMapper());
    }

    /**
     * Insere um novo usuário na tabela.
     *
     * @param usuario O usuário a ser salvo.
     * @return O número de linhas afetadas (deve ser 1 se a inserção for bem-sucedida).
     */
    public int save(Usuario usuario) {
        String sql =
                "INSERT INTO public.usuarios (nome, email, senha, id_nivel_acesso, ativo) " +
                        "VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getIdNivelAcesso(), usuario.getAtivo());
    }

    /**
     * Atualiza um usuário existente na tabela.
     *
     * @param usuario O usuário a ser atualizado.
     * @return O número de linhas afetadas (deve ser 1 se a atualização for bem-sucedida).
     */
    public int update(Usuario usuario) {
        String sql =
                "UPDATE public.usuarios SET nome = ?, email = ?, senha = ?, id_nivel_acesso = ?, ativo = ? " +
                        "WHERE id = ?";
        return jdbcTemplate.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getIdNivelAcesso(), usuario.getAtivo(), usuario.getId());
    }

    /**
     * Remove um usuário da tabela pelo ID fornecido.
     *
     * @param id O ID do usuário a ser removido.
     * @return O número de linhas afetadas (deve ser 1 se a exclusão for bem-sucedida).
     */
    public int delete(Integer id) {
        String sql =
                "DELETE FROM public.usuarios " +
                        "WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    /**
     * Classe interna que implementa RowMapper para mapear linhas de resultados SQL em objetos Usuario.
     */
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
