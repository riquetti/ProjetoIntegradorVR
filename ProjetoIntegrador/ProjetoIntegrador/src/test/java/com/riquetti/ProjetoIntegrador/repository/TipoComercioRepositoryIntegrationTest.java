package com.riquetti.ProjetoIntegrador.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.riquetti.ProjetoIntegrador.entity.TipoComercio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

public class TipoComercioRepositoryIntegrationTest {

    private TipoComercioRepository tipoComercioRepository;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = DataSourceConfig.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
        tipoComercioRepository = new TipoComercioRepository(jdbcTemplate);

        // Criar a tabela tipo_comercio antes de realizar os testes
        jdbcTemplate.execute("CREATE TABLE tipo_comercio (" +
                "id_tipo_comercio BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "descricao VARCHAR(255));");
    }

    @AfterEach
    public void tearDown() {
        // Limpar dados do banco de dados (se necessário)
        jdbcTemplate.execute("DROP ALL OBJECTS");
    }

    @Test
    public void testSaveAndFindById() {
        // Criar e salvar um novo TipoComercio
        TipoComercio tipoComercio = new TipoComercio();
        tipoComercio.setDescricao("Comércio A");

        // Salvar e capturar o ID gerado
        int rowsAffected = tipoComercioRepository.save(tipoComercio);
        assertEquals(1, rowsAffected); // Verificar se uma linha foi afetada

        // Buscar pelo TipoComercio usando o ID gerado
        Long generatedId = tipoComercio.getIdTipoComercio(); // Assumindo que o ID é setado no objeto após a inserção

        // Buscando por ID
        Optional<TipoComercio> foundOptional = tipoComercioRepository.findById(generatedId);

        // Verificações
        assertTrue(foundOptional.isPresent(), "O tipo de comércio deve ser encontrado.");
        TipoComercio found = foundOptional.get();
        assertEquals("Comércio A", found.getDescricao());
    }


}
