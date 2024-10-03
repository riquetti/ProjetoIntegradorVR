package com.riquetti.ProjetoIntegrador.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.riquetti.ProjetoIntegrador.entity.TipoComercio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TipoComercioRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TipoComercioRepository tipoComercioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testFindAll() {
        List<TipoComercio> expectedList = Arrays.asList(
                new TipoComercio(1L, "Comércio A"),
                new TipoComercio(2L, "Comércio B")
        );

        // Mockando a consulta no banco
        when(jdbcTemplate.query(anyString(), any(TipoComercioRepository.TipoComercioRowMapper.class)))
                .thenReturn(expectedList);

        List<TipoComercio> result = tipoComercioRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Comércio A", result.get(0).getDescricao());

        verify(jdbcTemplate, times(1)).query(anyString(), any(TipoComercioRepository.TipoComercioRowMapper.class));
    }

    @Test
    public void testSave() {
        TipoComercio tipoComercio = new TipoComercio();
        tipoComercio.setDescricao("Comércio A");

        // Mockando a atualização no banco
        when(jdbcTemplate.update(anyString(), any(Object[].class)))
                .thenReturn(1);

        int result = tipoComercioRepository.save(tipoComercio);

        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    public void testUpdate() {
        TipoComercio tipoComercio = new TipoComercio();
        tipoComercio.setIdTipoComercio(1L);
        tipoComercio.setDescricao("Comércio A");

        // Mockando a atualização no banco
        when(jdbcTemplate.update(anyString(), any(Object[].class)))
                .thenReturn(1);

        int result = tipoComercioRepository.update(tipoComercio);

        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        // Mockando a exclusão no banco
        when(jdbcTemplate.update(anyString(), any(Object[].class)))
                .thenReturn(1);

        int result = tipoComercioRepository.delete(id);

        assertEquals(1, result);
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

}
