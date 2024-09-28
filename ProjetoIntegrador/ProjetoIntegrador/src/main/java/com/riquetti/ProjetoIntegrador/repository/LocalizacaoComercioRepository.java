package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.LocalizacaoComercio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Repositório para gerenciar operações relacionadas a LocalizacaoComercio.
 * Esta classe fornece métodos para buscar, inserir, atualizar e excluir
 * registros da tabela localizacao_comercios.
 */

@Repository
public class LocalizacaoComercioRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Construtor para injeção de dependência do JdbcTemplate.
     *
     * @param jdbcTemplate O JdbcTemplate utilizado para interagir com o banco de dados.
     */
    public LocalizacaoComercioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Encontra um comércio pela ID.
     *
     * @param idComercio O ID do comércio a ser encontrado.
     * @return O objeto LocalizacaoComercio correspondente ao ID fornecido.
     */
    public LocalizacaoComercio findById(Long idComercio) {
        String sql =
                "SELECT id_comercio, nome, descricao, id_tipo_comercio, raio_acao_metros, " +
                "ST_AsText(localizacao) AS localizacao_texto " +
                "FROM public.localizacao_comercios WHERE id_comercio = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{idComercio}, new LocalizacaoComercioRowMapper());
    }

    /**
     * Encontra todos os comércios.
     *
     * @return Uma lista de todos os objetos LocalizacaoComercio.
     */
    public List<LocalizacaoComercio> findAll() {
        String sql =
                "SELECT id_comercio, nome, descricao, id_tipo_comercio, raio_acao_metros, " +
                "ST_AsText(localizacao) AS localizacao_texto " +
                "FROM public.localizacao_comercios";
        return jdbcTemplate.query(sql, new LocalizacaoComercioRowMapper());
    }

    /**
     * Salva um novo comércio.
     *
     * @param comercio O objeto LocalizacaoComercio a ser salvo.
     * @return O número de linhas afetadas pela operação.
     */
    public int save(LocalizacaoComercio comercio) {
        String sql =
                "INSERT INTO public.localizacao_comercios (nome, descricao, id_tipo_comercio, raio_acao_metros, localizacao) " +
                "VALUES (?, ?, ?, ?, ST_GeomFromText(?, 4326))";
        return jdbcTemplate.update(sql, comercio.getNome(), comercio.getDescricao(),
                comercio.getIdTipoComercio(), comercio.getRaioAcaoMetros(), comercio.getLocalizacao().toString());
    }

    /**
     * Atualiza um comércio existente.
     *
     * @param comercio O objeto LocalizacaoComercio com os dados atualizados.
     * @return O número de linhas afetadas pela operação.
     */
    public int update(LocalizacaoComercio comercio) {
        String sql = "UPDATE public.localizacao_comercios SET nome = ?, descricao = ?, id_tipo_comercio = ?, " +
                "raio_acao_metros = ?, localizacao = ST_GeomFromText(?, 4326) " +
                "WHERE id_comercio = ?";
        return jdbcTemplate.update(sql, comercio.getNome(), comercio.getDescricao(), comercio.getIdTipoComercio(),
                comercio.getRaioAcaoMetros(), comercio.getLocalizacao().toString(), comercio.getIdComercio());
    }

    /**
     * Exclui um comércio pelo ID.
     *
     * @param idComercio O ID do comércio a ser excluído.
     * @return O número de linhas afetadas pela operação.
     */
    public int delete(Long idComercio) {
        String sql = "DELETE FROM public.localizacao_comercios WHERE id_comercio = ?";
        return jdbcTemplate.update(sql, idComercio);
    }

    /**
     * Classe interna para mapear os resultados da consulta para o objeto LocalizacaoComercio.
     */
    private static class LocalizacaoComercioRowMapper implements RowMapper<LocalizacaoComercio> {
        @Override
        public LocalizacaoComercio mapRow(ResultSet rs, int rowNum) throws SQLException {
            LocalizacaoComercio comercio = new LocalizacaoComercio();
            comercio.setIdComercio(rs.getLong("id_comercio"));
            comercio.setNome(rs.getString("nome"));
            comercio.setDescricao(rs.getString("descricao"));
            comercio.setIdTipoComercio(rs.getLong("id_tipo_comercio"));
            comercio.setRaioAcaoMetros(rs.getBigDecimal("raio_acao_metros"));
            comercio.setLocalizacao(rs.getString("localizacao_texto"));

            return comercio;
        }
    }

}
