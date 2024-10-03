package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.TipoComercioDTO;
import com.riquetti.ProjetoIntegrador.entity.TipoComercio;
import com.riquetti.ProjetoIntegrador.mapper.TipoComercioMapper;
import com.riquetti.ProjetoIntegrador.repository.TipoComercioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TipoComercioServiceTest {
    @InjectMocks
    private TipoComercioService tipoComercioService;

    @Mock
    private TipoComercioRepository tipoComercioRepository;

    private TipoComercioMapper tipoComercioMapper = TipoComercioMapper.INSTANCE;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        // Configurar o mock
        TipoComercio tipoComercio = new TipoComercio(1L, "Comércio A");
        when(tipoComercioRepository.findById(1L)).thenReturn(Optional.of(tipoComercio)); // Usando Optional

        // Chamar o método
        TipoComercioDTO result = tipoComercioService.findById(1L);

        // Verificações
        assertNotNull(result);
        assertEquals("Comércio A", result.descricao());
        verify(tipoComercioRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll() {
        // Configurar o mock
        TipoComercio tipoComercioA = new TipoComercio(1L, "Comércio A");
        TipoComercio tipoComercioB = new TipoComercio(2L, "Comércio B");
        when(tipoComercioRepository.findAll()).thenReturn(Arrays.asList(tipoComercioA, tipoComercioB));

        // Chamar o método
        List<TipoComercioDTO> result = tipoComercioService.findAll();

        // Verificações
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Comércio A", result.get(0).descricao());
        assertEquals("Comércio B", result.get(1).descricao());
        verify(tipoComercioRepository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        // Configurar o DTO e o mock
        TipoComercioDTO tipoComercioDTO = new TipoComercioDTO(1L, "Comércio A");
        TipoComercio tipoComercio = tipoComercioMapper.toEntity(tipoComercioDTO);

        // Chamar o método
        tipoComercioService.save(tipoComercioDTO);

        // Verificações
        verify(tipoComercioRepository, times(1)).save(any(TipoComercio.class));
    }

    @Test
    public void testUpdate() {
        // Configurar o DTO
        TipoComercioDTO tipoComercioDTO = new TipoComercioDTO(1L, "Comércio Atualizado");

        // Chamar o método
        tipoComercioService.update(tipoComercioDTO);

        // Verificações
        verify(tipoComercioRepository, times(1)).update(any(TipoComercio.class)); // O mock espera uma entidade
    }

    @Test
    public void testDelete() {
        // Chamar o método
        tipoComercioService.delete(1L);

        // Verificações
        verify(tipoComercioRepository, times(1)).delete(1L);
    }

    @Test
    public void testFindByIdThrowsExceptionWhenIdIsNull() {
        // Verificações
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tipoComercioService.findById(null);
        });
        assertEquals("O idTipoComercio deve ser um número inteiro positivo e não pode ser nulo.", exception.getMessage());
    }

    @Test
    public void testSaveThrowsExceptionWhenDTOIsInvalid() {
        TipoComercioDTO invalidDTO = new TipoComercioDTO(null, null); // Descrição nula

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tipoComercioService.save(invalidDTO);
        });
        assertEquals("A descrição do tipo de comércio não pode ser nula ou vazia.", exception.getMessage());
    }
}
