package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.entity.ComercioDadosIbge;
import com.riquetti.ProjetoIntegrador.repository.ComercioDadosIbgeRepository;
import com.riquetti.ProjetoIntegrador.dto.ComercioDadosIbgeDTO;
import com.riquetti.ProjetoIntegrador.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ComercioDadosIbgeServiceTest {

    @Mock
    private ComercioDadosIbgeRepository repository;

    @InjectMocks
    private ComercioDadosIbgeService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Método para criar dados simulados
    private List<ComercioDadosIbge> criarDadosSimulados() {
        List<ComercioDadosIbge> comercios = new ArrayList<>();

        // Criar instâncias de ComercioDadosIbge
        ComercioDadosIbge comercio1 = new ComercioDadosIbge();
        comercio1.setIdComercio(1L);
        comercio1.setNome("Comércio A");


        ComercioDadosIbge comercio2 = new ComercioDadosIbge();
        comercio2.setIdComercio(2L);
        comercio2.setNome("Comércio B");


        // Adicionar os comercios à lista
        comercios.add(comercio1);
        comercios.add(comercio2);

        return comercios;
    }

    @Test
    public void testGetLocalizacaoComercioById_ValidId() {
        Long validId = 1L;
        List<ComercioDadosIbge> mockData = criarDadosSimulados();

                when(repository.findByIdComercio(validId)).thenReturn(mockData);

        List<ComercioDadosIbgeDTO> result = service.getLocalizacaoComercioById(validId);

        assertNotNull(result);
        assertEquals(mockData.size(), result.size());
        verify(repository, times(1)).findByIdComercio(validId);
    }

    @Test
    public void testGetLocalizacaoComercioById_InvalidId() {
        Long invalidId = 999L;

        when(repository.findByIdComercio(invalidId)).thenReturn(Collections.emptyList());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.getLocalizacaoComercioById(invalidId);
        });

        assertEquals("Comércio com ID " + invalidId + " não encontrado.", exception.getMessage());
    }

    @Test
    public void testGetLocalizacaoComercioById_NullId() {
        Long nullId = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getLocalizacaoComercioById(nullId);
        });

        assertEquals("O ID do comércio deve ser um número positivo e não nulo.", exception.getMessage());
    }

    @Test
    public void testGetAllLocalizacaoComercio() {
        List<ComercioDadosIbge> mockData = criarDadosSimulados();

                when(repository.findAll()).thenReturn(mockData);

        List<ComercioDadosIbgeDTO> result = service.getAllLocalizacaoComercio();

        assertNotNull(result);
        assertEquals(mockData.size(), result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetLocalizacaoComercioByIdWithRaio_ValidParameters() {
        Long validId = 1L;
        Double validRaio = 1000.0;
        List<ComercioDadosIbge> mockData = criarDadosSimulados();

                when(repository.findByIdComercioWithRaio(validId, validRaio)).thenReturn(mockData);

        List<ComercioDadosIbgeDTO> result = service.getLocalizacaoComercioByIdWithRaio(validId, validRaio);

        assertNotNull(result);
        assertEquals(mockData.size(), result.size());
        verify(repository, times(1)).findByIdComercioWithRaio(validId, validRaio);
    }

    @Test
    public void testGetLocalizacaoComercioByPontoAndRaio_ValidParameters() {
        String pontoTexto = "POINT(-47.4148445 -22.5936527)";
        Long raio = 500L;
        List<ComercioDadosIbge> mockData = criarDadosSimulados();

                when(repository.findByLocationAndRaio(pontoTexto, raio)).thenReturn(mockData);

        List<ComercioDadosIbgeDTO> result = service.getLocalizacaoComercioByPontoAndRaio(pontoTexto, raio);

        assertNotNull(result);
        assertEquals(mockData.size(), result.size());
        verify(repository, times(1)).findByLocationAndRaio(pontoTexto, raio);
    }

}
