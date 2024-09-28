package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.NivelAcesso;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repositório para gerenciar operações relacionadas a NivelAcesso.
 * Esta classe fornece métodos para buscar, inserir, atualizar e excluir
 * registros da tabela nivel_acesso.
 */
@Repository
public class NivelAcessoRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Construtor para a classe NivelAcessoRepository.
     *
     * @param jdbcTemplate JdbcTemplate para realizar operações de banco de dados.
     */
    public NivelAcessoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recupera um nível de acesso pelo ID fornecido.
     *
     * @param idNivelAcesso O ID do nível de acesso a ser recuperado.
     * @return Um objeto NivelAcesso correspondente ao ID especificado.
     */
    public NivelAcesso findById(Long idNivelAcesso) {
        String sql =
                "SELECT * FROM public.nivel_acesso WHERE id_nivel_acesso = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idNivelAcesso}, new NivelAcessoRowMapper());
    }

    /**
     * Recupera todos os níveis de acesso no banco de dados.
     *
     * @return Uma lista de objetos NivelAcesso contendo todos os registros na tabela nivel_acesso.
     */
    public List<NivelAcesso> findAll() {
        String sql =
                "SELECT * FROM public.nivel_acesso";
        return jdbcTemplate.query(sql, new NivelAcessoRowMapper());
    }

    /**
     * Insere um novo nível de acesso na tabela.
     *
     * @param nivelAcesso O nível de acesso a ser salvo.
     * @return O número de linhas afetadas (deve ser 1 se a inserção for bem-sucedida).
     */
    public int save(NivelAcesso nivelAcesso) {
        String sql =
                "INSERT INTO public.nivel_acesso (nivel_acesso) VALUES (?)";
        return jdbcTemplate.update(sql, nivelAcesso.getNivelAcesso());
    }

    /**
     * Atualiza um nível de acesso existente na tabela.
     *
     * @param nivelAcesso O nível de acesso a ser atualizado.
     * @return O número de linhas afetadas (deve ser 1 se a atualização for bem-sucedida).
     */
    public int update(NivelAcesso nivelAcesso) {
        String sql =
                "UPDATE public.nivel_acesso SET nivel_acesso = ? WHERE id_nivel_acesso = ?";
        return jdbcTemplate.update(sql, nivelAcesso.getNivelAcesso(), nivelAcesso.getIdNivelAcesso());
    }

    /**
     * Remove um nível de acesso da tabela pelo ID fornecido.
     *
     * @param idNivelAcesso O ID do nível de acesso a ser removido.
     * @return O número de linhas afetadas (deve ser 1 se a exclusão for bem-sucedida).
     */
    public int delete(Long idNivelAcesso) {
        String sql =
                "DELETE FROM public.nivel_acesso WHERE id_nivel_acesso = ?";
        return jdbcTemplate.update(sql, idNivelAcesso);
    }

    /**
     * Classe interna que implementa RowMapper para mapear linhas de resultados SQL em objetos NivelAcesso.
     */
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
