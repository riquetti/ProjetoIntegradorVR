package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.service.IBGEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para gerenciar as consultas da API do IBGE.
 * Este controlador expõe endpoints para obtém a malha de um município específico
 * em formato GeoJSON a partir da API do IBGE.
 */
@RestController
public class IBGEViewController {

    @Autowired
    private IBGEService ibgeService;

    /**
     * Obtém o GeoJSON da malha do município especificado.
     *
     * @param municipioId O ID do município, não pode ser nulo ou vazio.
     * @return O GeoJSON da malha do município ou uma mensagem de erro.
     */
    @GetMapping("/malha/{municipioId}")
    public ResponseEntity<?> getMalhaMunicipio(@PathVariable String municipioId) {
        // Verifica se municipioId está nulo ou vazio
        if (municipioId == null || municipioId.trim().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID do município não pode ser nulo ou vazio.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Chama o serviço para obter o GeoJSON da malha do município
        String geoJson = ibgeService.getMalhaMunicipio(municipioId);

        // Verifica se o município foi encontrado
        if (geoJson == null || geoJson.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Município não encontrado para o ID especificado: " + municipioId);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        // Retorna o GeoJSON se o município for encontrado
        return new ResponseEntity<>(geoJson, HttpStatus.OK);
    }
}

