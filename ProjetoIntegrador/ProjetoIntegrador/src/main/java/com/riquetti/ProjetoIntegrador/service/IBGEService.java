package com.riquetti.ProjetoIntegrador.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IBGEService {

    public String getMalhaMunicipio(String municipioId) {
        // URL da API IBGE para obter a malha do município em GeoJSON
        String url = "https://servicodados.ibge.gov.br/api/v3/malhas/municipios/" + municipioId + "?formato=application/vnd.geo+json";

        // Usar RestTemplate para fazer a requisição
        RestTemplate restTemplate = new RestTemplate();
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
