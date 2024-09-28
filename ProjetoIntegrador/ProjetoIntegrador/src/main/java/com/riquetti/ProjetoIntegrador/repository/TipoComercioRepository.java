package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.TipoComercio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repositório para gerenciar o acesso a dados relacionados ao tipo de comércio.
 * Esta classe utiliza JdbcTemplate para executar operações de banco de dados.
 */
@Repository
public class TipoComercioRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Construtor que inicializa o repositório com o JdbcTemplate.
     *
     * @param jdbcTemplate Instância do JdbcTemplate para operações de banco de dados.
     */
    public TipoComercioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recupera um tipo de comércio pelo ID fornecido.
     *
     * @param idTipoComercio O ID do tipo de comércio a ser recuperado.
     * @return Um objeto TipoComercio correspondente ao ID especificado.
     */
    public TipoComercio findById(Long idTipoComercio) {
        String sql =
                "SELECT * FROM public.tipo_comercio WHERE id_tipo_comercio = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idTipoComercio}, new TipoComercioRowMapper());
    }

    /**
     * Recupera todos os tipos de comércio no banco de dados.
     *
     * @return Uma lista de objetos TipoComercio contendo todos os registros na tabela tipo_comercio.
     */
    public List<TipoComercio> findAll() {
        String sql =
                "SELECT * FROM public.tipo_comercio";
        return jdbcTemplate.query(sql, new TipoComercioRowMapper());
    }

    /**
     * Insere um novo tipo de comércio na tabela.
     *
     * @param tipoComercio O tipo de comércio a ser salvo.
     * @return O número de linhas afetadas (deve ser 1 se a inserção for bem-sucedida).
     */
    public int save(TipoComercio tipoComercio) {
        String sql =
                "INSERT INTO public.tipo_comercio (descricao) VALUES (?)";
        return jdbcTemplate.update(sql, tipoComercio.getDescricao());
    }

    /**
     * Atualiza um tipo de comércio existente na tabela.
     *
     * @param tipoComercio O tipo de comércio a ser atualizado.
     * @return O número de linhas afetadas (deve ser 1 se a atualização for bem-sucedida).
     */
    public int update(TipoComercio tipoComercio) {
        String sql =
                "UPDATE public.tipo_comercio SET descricao = ? WHERE id_tipo_comercio = ?";
        return jdbcTemplate.update(sql, tipoComercio.getDescricao(), tipoComercio.getIdTipoComercio());
    }

    /**
     * Remove um tipo de comércio da tabela pelo ID fornecido.
     *
     * @param idTipoComercio O ID do tipo de comércio a ser removido.
     * @return O número de linhas afetadas (deve ser 1 se a exclusão for bem-sucedida).
     */
    public int delete(Long idTipoComercio) {
        String sql =
                "DELETE FROM public.tipo_comercio WHERE id_tipo_comercio = ?";
        return jdbcTemplate.update(sql, idTipoComercio);
    }

    /**
     * Classe interna que implementa RowMapper para mapear linhas de resultados SQL em objetos TipoComercio.
     */
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
