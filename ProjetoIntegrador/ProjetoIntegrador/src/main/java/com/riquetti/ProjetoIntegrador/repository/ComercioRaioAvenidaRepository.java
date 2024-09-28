package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.ComercioRaioAvenida;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório responsável pela interação com a tabela `localizacao_comercios`
 * e pela execução de consultas relacionadas a comércios, suas localizações e
 * dados de vias do IBGE.
 * Os Dados são utilizados para cálcular de contagem de vias e avenidas.
 * No Banco de Dados foi utilizado a extensão postgis para análises espaciais.
 */
@Repository
public class ComercioRaioAvenidaRepository {

    /**
     *
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * Construtor para injeção de dependência do JdbcTemplate.
     *
     * @param jdbcTemplate o JdbcTemplate utilizado para executar as consultas.
     */
    public ComercioRaioAvenidaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Busca todos os comércios e suas informações, incluindo a localização
     * e a contagem de ruas em diferentes distâncias.
     *
     * @return uma lista de objetos ComercioRaioAvenida representando todos os comércios e sus informações.
     */
    public List<ComercioRaioAvenida> findAllComercios() {
        String sql = "SELECT loc.id_comercio, loc.nome, ST_AsText(loc.localizacao) AS localizacao_texto, " +
                "loc.raio_acao_metros, " +
                // Utiliza a localização, gera um buffer considerando o raio de ação, intercepta com a base de vias do IBGE e realiza a contagem
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, 200)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_200m, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, 500)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_500m, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, 1000)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_1km, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, 1500)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_1_5km, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, 2000)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_2km, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, loc.raio_acao_metros)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_raio_acao_metros " +
                "FROM localizacao_comercios AS loc " +
                "JOIN \"SHP_Ruas\" AS ruas ON ruas.nm_tip_log IN ('AVENIDA', 'VIA') " +
                "GROUP BY loc.id_comercio, loc.nome, loc.localizacao, loc.raio_acao_metros";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ComercioRaioAvenida(
                rs.getLong("id_comercio"),
                rs.getString("nome"),
                rs.getString("localizacao_texto"),
                rs.getLong("raio_acao_metros"),
                rs.getInt("total_200m"),
                rs.getInt("total_500m"),
                rs.getInt("total_1km"),
                rs.getInt("total_1_5km"),
                rs.getInt("total_2km"),
                rs.getInt("total_raio_acao_metros")
        ));
    }

    /**
     * Recupera comercios filtrados pelo id_comercio.
     *
     * @param idComercio idComercio o id do comercio a ser filtrado.
     * @return uma lista de ComercioRaioAvenida contendo os comercios que correspondem ao id fornecido.
     */
    public List<ComercioRaioAvenida> findComerciosById(Long idComercio) {
        String sql = "SELECT loc.id_comercio, loc.nome, ST_AsText(loc.localizacao) AS localizacao_texto, " +
                "loc.raio_acao_metros," +
                // Utiliza a localização, gera um buffer considerando o raio de ação, intercepta com a base de vias do IBGE e realiza a contagem
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, 200)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_200m, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, 500)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_500m, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, 1000)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_1km, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, 1500)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_1_5km, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, 2000)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_2km, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, loc.raio_acao_metros)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_raio_acao_metros " +
                "FROM localizacao_comercios AS loc " +
                "JOIN \"SHP_Ruas\" AS ruas ON ruas.nm_tip_log IN ('AVENIDA', 'VIA') " +
                "WHERE loc.id_comercio = ? " +
                "GROUP BY loc.id_comercio, loc.nome, loc.localizacao, loc.raio_acao_metros";
        return jdbcTemplate.query(sql, new Object[]{idComercio}, (rs, rowNum) -> new ComercioRaioAvenida(
                rs.getLong("id_comercio"),
                rs.getString("nome"),
                rs.getString("localizacao_texto"),
                rs.getLong("raio_acao_metros"),
                rs.getInt("total_200m"),
                rs.getInt("total_500m"),
                rs.getInt("total_1km"),
                rs.getInt("total_1_5km"),
                rs.getInt("total_2km"),
                rs.getInt("total_raio_acao_metros")
        ));
    }

    /**
     * Recupera comercio filtrado pelo id_comercio e raio de ação.
     *
     * @param idComercio        idComercio o id do comercio a ser filtrado.
     * @param raioAcaoMetros    raioAcaoMetros o raio de ação a ser considerado na busca.
     * @return um objeto ComercioRaioAvenida correspondente ao comercio filtrado.
     */
    public ComercioRaioAvenida findComercioByIdAndRaio(Long idComercio, Double raioAcaoMetros) {
        String sql = "SELECT loc.id_comercio, loc.nome, ST_AsText(loc.localizacao) AS localizacao_texto, " +
                // Utiliza a localização, gera um buffer considerando o raio de ação, intercepta com a base de vias do IBGE e realiza a contagem
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(loc.localizacao::geography, ?)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_raio_acao_metros " +
                "FROM localizacao_comercios AS loc " +
                "JOIN \"SHP_Ruas\" AS ruas ON ruas.nm_tip_log IN ('AVENIDA', 'VIA') " +
                "WHERE loc.id_comercio = ? " +
                "GROUP BY loc.id_comercio, loc.nome, loc.localizacao";

        return jdbcTemplate.queryForObject(sql, new Object[]{raioAcaoMetros, idComercio}, (rs, rowNum) -> new ComercioRaioAvenida(
                rs.getLong("id_comercio"),
                rs.getString("nome"),
                rs.getString("localizacao_texto"),
                raioAcaoMetros.longValue(), // Converte para Long para a entidade
                0, 0, 0, 0, 0,  // valores dummy para os outros campos
                rs.getInt("total_raio_acao_metros")
        ));
    }

    /**
     * Busca dados filtrados pela localização fornecida.
     *
     * @param localizacaoTexto  localizacaoTexto a localização em formato POINT(-47.4042319 -22.561447) a ser filtrada.
     * @return uma lista de ComercioRaioAvenida contendo os resultados da busca.
     */
    public List<ComercioRaioAvenida> findPontoRaioAvenida(String localizacaoTexto) {
        // Extrair longitude e latitude do texto POINT(-47.4042319 -22.561447)
        String[] coordinates = localizacaoTexto.replace("POINT(", "").replace(")", "").split(" ");
        double longitude = Double.parseDouble(coordinates[0]);
        double latitude = Double.parseDouble(coordinates[1]);

        // Definir o ponto uma vez para reutilizá-lo nos buffers
        String sql = "WITH ponto AS (" +
                "SELECT ST_SetSRID(ST_MakePoint(?, ?), 4326) AS geom" +
                ") " +
                "SELECT " +
                "0 AS id_comercio, " +  // Definindo id_comercio como 0
                "'Ponto de consulta' AS nome, " +  // Definindo nome como 'Ponto de consulta'
                "ST_AsText(p.geom) AS localizacao_texto, " +  // Retorna a localização do ponto
                // Utiliza a localização, gera um buffer considerando o raio de ação, intercepta com a base de vias do IBGE e realiza a contagem
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(p.geom::geography, 200)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_200m, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(p.geom::geography, 500)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_500m, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(p.geom::geography, 1000)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_1km, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(p.geom::geography, 1500)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_1_5km, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(p.geom::geography, 2000)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_2km " +
                "FROM ponto p, \"SHP_Ruas\" AS ruas " +
                "WHERE ruas.nm_tip_log IN ('AVENIDA', 'VIA') " +
                "GROUP BY p.geom";

        // Executar a consulta com os parâmetros de latitude e longitude
        return jdbcTemplate.query(sql, new Object[]{longitude, latitude}, (rs, rowNum) -> new ComercioRaioAvenida(
                0L,  // id_comercio sempre como 0
                "Ponto de consulta",  // nome sempre como 'Ponto de consulta'
                rs.getString("localizacao_texto"),
                0L,  // raioAcaoMetros como 0
                rs.getInt("total_200m"),
                rs.getInt("total_500m"),
                rs.getInt("total_1km"),
                rs.getInt("total_1_5km"),
                rs.getInt("total_2km"),
                0  // total_raio_acao_metros removido e configurado para 0
        ));
    }

    /**
     * Método para buscar dados filtrados pela localização e raio de ação.
     *
     * @param localizacaoTexto a localização em formato POINT (ex: POINT(-47.4042319 -22.561447)) a ser filtrada.
     * @param raioAcaopersolizado o raio de ação em metros para calcular a quantidade de ruas dentro desse raio.
     * @return uma lista de ComercioRaioAvenida contendo informações sobre o ponto de consulta e a contagem de ruas nas diferentes distâncias.
     */
    public List<ComercioRaioAvenida> findLocalizacaoRaioAvenida(String localizacaoTexto, double raioAcaopersolizado) {
        // Extrair longitude e latitude do texto POINT(-47.4042319 -22.561447)
        String[] coordinates = localizacaoTexto.replace("POINT(", "").replace(")", "").split(" ");
        double longitude = Double.parseDouble(coordinates[0]);
        double latitude = Double.parseDouble(coordinates[1]);

        // Definir o ponto uma vez para reutilizá-lo nos buffers
        String sql = "WITH ponto AS (" +
                "SELECT ST_SetSRID(ST_MakePoint(?, ?), 4326) AS geom" +
                ") " +
                "SELECT " +
                "0 AS id_comercio, " +  // Definindo id_comercio como 0
                "'Ponto de consulta' AS nome, " +  // Definindo nome como 'Ponto de consulta'
                "ST_AsText(p.geom) AS localizacao_texto, " +  // Retorna a localização do ponto
                // Utiliza a localização, gera um buffer considerando o raio de ação, intercepta com a base de vias do IBGE e realiza a contagem
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(p.geom::geography, 200)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_200m, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(p.geom::geography, 500)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_500m, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(p.geom::geography, 1000)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_1km, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(p.geom::geography, 1500)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_1_5km, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(p.geom::geography, 2000)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_2km, " +
                "COUNT(CASE WHEN ST_Intersects(ST_Buffer(p.geom::geography, ?)::geometry, ruas.geom::geometry) THEN 1 ELSE NULL END) AS total_raio_acao_metros " +
                "FROM ponto p, \"SHP_Ruas\" AS ruas " +
                "WHERE ruas.nm_tip_log IN ('AVENIDA', 'VIA') " +
                "GROUP BY p.geom";

        // Executar a consulta com os parâmetros de latitude, longitude e raio de ação
        return jdbcTemplate.query(sql, new Object[]{longitude, latitude, raioAcaopersolizado}, (rs, rowNum) -> new ComercioRaioAvenida(
                0L,  // id_comercio sempre como 0
                "Ponto de consulta",  // nome sempre como 'Ponto de consulta'
                rs.getString("localizacao_texto"),
                (long) raioAcaopersolizado,  // Passando o valor do raio de ação
                rs.getInt("total_200m"),
                rs.getInt("total_500m"),
                rs.getInt("total_1km"),
                rs.getInt("total_1_5km"),
                rs.getInt("total_2km"),
                rs.getInt("total_raio_acao_metros")  // O total do raio de ação agora usa o valor passado
        ));
    }

}
