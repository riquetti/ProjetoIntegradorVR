package com.riquetti.ProjetoIntegrador.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Serviço responsável por interagir com a API do IBGE para obter a malha de um município
 * específico em formato GeoJSON.
 */
@Service
public class IBGEService {

    private final RestTemplate restTemplate;

    // Injeção do RestTemplate via construtor
    public IBGEService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtém a malha de um município específico em formato GeoJSON a partir da API do IBGE.
     *
     * @param municipioId o ID do município para o qual a malha será obtida.
     * @return uma String contendo a malha do município em formato GeoJSON.
     *         Em caso de erro, retorna uma mensagem de erro.
     */
    public String getMalhaMunicipio(String municipioId) {
        // URL da API IBGE para obter a malha do município em GeoJSON
        String url = "https://servicodados.ibge.gov.br/api/v3/malhas/municipios/" + municipioId + "?formato=application/vnd.geo+json";

        try {
            // Fazer a requisição e obter o GeoJSON como String
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            // Log e retorno de erro em caso de falha
            System.err.println("Erro ao buscar malha do município: " + e.getMessage());
            return "Erro ao buscar malha do município";
        }
    }
}