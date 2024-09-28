package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.Concorrente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório responsável pela interação com a tabela `localizacao_comercios`
 * e pela execução de consultas relacionadas a comércios e suas localizações
 * dados dos comécios concorrentes.
 */
@Repository
public class ConcorrenteRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Construtor da classe ConcorrenteRepository.
     *
     * Este construtor inicializa o repositório com um objeto JdbcTemplate,
     * que é utilizado para realizar operações de acesso a dados no banco de dados.
     *
     * @param jdbcTemplate o objeto JdbcTemplate que será usado para executar
     *                    consultas e comandos SQL.
     */
    public ConcorrenteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Encontra concorrentes dentro de um raio especificado a partir de um comércio.
     *
     * Este método utiliza uma consulta SQL que busca todos os comércios (concorrentes)
     * localizados a uma distância definida a partir da localização de um comércio específico.
     *
     * @param idComercio   O ID do comércio a partir do qual o raio será calculado.
     * @param raioMetros   O raio em metros dentro do qual os concorrentes serão buscados.
     * @return Uma lista de objetos oncorrente que representam os comércios concorrentes
     *         localizados dentro do raio especificado.
     */
    public List<Concorrente> findByRaio(Long idComercio, Long raioMetros) {
        String sql =
                "WITH origem AS (\n" +
                        "  SELECT localizacao \n" +
                        "  FROM localizacao_comercios \n" +
                        "  WHERE id_comercio = ?\n" +
                        ")\n" +
                        "SELECT lc.id_comercio, ST_AsText(lc.localizacao) as localizacao, lc.nome, lc.descricao, lc.id_tipo_comercio\n" +  // Conversão para texto
                        "FROM localizacao_comercios lc, origem o\n" +
                        "WHERE ST_DWithin(lc.localizacao, o.localizacao, ?)\n" +
                        "  AND lc.id_comercio != ?;";

        return jdbcTemplate.query(sql, new Object[]{idComercio, raioMetros, idComercio}, (rs, rowNum) -> {
            Concorrente concorrente = new Concorrente();
            concorrente.setIdComercio(rs.getLong("id_comercio"));
            concorrente.setNome(rs.getString("nome"));
            concorrente.setDescricao(rs.getString("descricao"));
            concorrente.setIdTipoComercio(rs.getLong("id_tipo_comercio"));
            concorrente.setLocalizacao(rs.getString("localizacao"));  // Agora será texto WKT
            return concorrente;
        });
    }

    /**
     * Encontra concorrentes utilizando o raio de ação do comércio especificado.
     *
     * Este método busca o raio de ação em metros a partir do comércio identificado
     * e utiliza esse valor para buscar concorrentes dentro desse raio.
     *
     * @param idComercio   O ID do comércio a partir do qual o raio será calculado.
     * @return Uma lista de objetos Concorrente que representam os comércios concorrentes
     *         localizados dentro do raio de ação do comércio especificado.
     */
    public List<Concorrente> findByRaioFromComercio(Long idComercio) {
        String sql =
                "SELECT lc.id_comercio, ST_AsText(lc.localizacao) as localizacao, lc.nome, lc.descricao, lc.id_tipo_comercio \n" +
                        "FROM localizacao_comercios lc \n" +
                        "WHERE lc.raio_acao_metros IS NOT NULL \n" +
                        "  AND ST_DWithin(lc.localizacao, (SELECT localizacao FROM localizacao_comercios WHERE id_comercio = ?), lc.raio_acao_metros) \n" +
                        "  AND lc.id_comercio != ?;";

        return jdbcTemplate.query(sql, new Object[]{idComercio, idComercio}, (rs, rowNum) -> {
            Concorrente concorrente = new Concorrente();
            concorrente.setIdComercio(rs.getLong("id_comercio"));
            concorrente.setNome(rs.getString("nome"));
            concorrente.setDescricao(rs.getString("descricao"));
            concorrente.setIdTipoComercio(rs.getLong("id_tipo_comercio"));
            concorrente.setLocalizacao(rs.getString("localizacao"));  // Agora será texto WKT
            return concorrente;
        });
    }
}

