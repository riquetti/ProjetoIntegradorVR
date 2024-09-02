package com.riquetti.ProjetoIntegrador.repository;

import com.riquetti.ProjetoIntegrador.entity.Concorrente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConcorrenteRepository {

    private final JdbcTemplate jdbcTemplate;

    public ConcorrenteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Concorrente> findByRaio(Long idComercio, Long raioMetros) {
        String sql =
                "WITH origem AS (\n" +
                        "  SELECT localizacao \n" +
                        "  FROM localizacao_comercios \n" +
                        "  WHERE id_comercio = ?\n" +
                        ")\n" +
                        "SELECT lc.id_comercio, lc.localizacao, lc.nome, lc.descricao, lc.id_tipo_comercio\n" +
                        "FROM localizacao_comercios lc, origem o\n" +
                        "WHERE ST_DWithin(lc.localizacao, o.localizacao, ?)\n" +
                        "  AND lc.id_comercio != ?;";

        return jdbcTemplate.query(sql, new Object[]{idComercio, raioMetros, idComercio}, (rs, rowNum) -> {
            Concorrente concorrente = new Concorrente();
            concorrente.setIdComercio(rs.getLong("id_comercio"));
            concorrente.setNome(rs.getString("nome"));
            concorrente.setDescricao(rs.getString("descricao"));
            concorrente.setIdTipoComercio(rs.getLong("id_tipo_comercio"));
            concorrente.setLocalizacao(rs.getString("localizacao"));  // ou outro tipo adequado
            return concorrente;
        });
    }

}
