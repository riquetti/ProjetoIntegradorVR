package com.riquetti.ProjetoIntegrador.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IBGEServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private IBGEService ibgeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenValidMunicipioId_thenReturnGeoJson() {
        String municipioId = "123456";
        String expectedGeoJson = "{\"type\": \"FeatureCollection\", \"features\": []}";
        String url = "https://servicodados.ibge.gov.br/api/v3/malhas/municipios/" + municipioId + "?formato=application/vnd.geo+json";

        when(restTemplate.getForObject(url, String.class)).thenReturn(expectedGeoJson);

        String result = ibgeService.getMalhaMunicipio(municipioId);

        assertEquals(expectedGeoJson, result);
        verify(restTemplate, times(1)).getForObject(url, String.class);
    }

    @Test
    void whenInvalidMunicipioId_thenReturnErrorMessage() {
        String municipioId = "000000";
        String url = "https://servicodados.ibge.gov.br/api/v3/malhas/municipios/" + municipioId + "?formato=application/vnd.geo+json";

        when(restTemplate.getForObject(url, String.class)).thenThrow(new RuntimeException("500 Internal Server Error"));

        String result = ibgeService.getMalhaMunicipio(municipioId);

        assertEquals("Erro ao buscar malha do município", result);
        verify(restTemplate, times(1)).getForObject(url, String.class);
    }

}
