package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioViasDTO;
import com.riquetti.ProjetoIntegrador.service.ComercioRaioViasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gerenciar as operações relacionadas aos dados de localização dos comércios vias próximas da base OSM.
 * Este controlador expõe endpoints para consultar os dados de comércios com base em diferentes parâmetros.
 */
@RestController
@RequestMapping("/api/comercio-raio-vias")
public class ComercioRaioViasController {

    private final ComercioRaioViasService service;

    public ComercioRaioViasController(ComercioRaioViasService service) {
        this.service = service;
    }

    /**
     * Obtém as vias de comércio pelo ID.
     *
     * @param id ID do comércio, deve ser um número inteiro maior que 0.
     * @return Lista de ComercioRaioViasDTO correspondente ao ID fornecido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getIdComercio(@PathVariable Long id) {
        // Verifica se o ID é nulo
        if (id == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID não pode ser nulo.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Verifica se o ID é maior que 0
        if (id <= 0) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID deve ser maior que 0.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Chama o serviço para buscar as vias do comércio
        List<ComercioRaioViasDTO> vias = service.getIdVias(id);
        return new ResponseEntity<>(vias, HttpStatus.OK);
    }

    /**
     * Obtém todas as vias de comércio.
     *
     * @return Lista de todas as ComercioRaioViasDTO.
     */
    @GetMapping
    public List<ComercioRaioViasDTO> getAllVias() {
        return service.getAllVias();
    }

    /**
     * Obtém as distâncias das vias de comércio pelo ID.
     *
     * @param id ID do comércio, deve ser um número inteiro maior que 0.
     * @return Lista de ComercioRaioViasDTO correspondente ao ID fornecido.
     */
    @GetMapping("/distancias/{id}")
    public ResponseEntity<?> getIdComercioDistancia(@PathVariable Long id) {
        // Verifica se o ID é nulo
        if (id == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID não pode ser nulo.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Verifica se o ID é maior que 0
        if (id <= 0) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID deve ser maior que 0.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Chama o serviço passando apenas o id do comércio
        List<ComercioRaioViasDTO> viasDistancia = service.findByComercioIdDistancia(id);
        return new ResponseEntity<>(viasDistancia, HttpStatus.OK);
    }

    /**
     * Obtém todas as distâncias das vias de comércio.
     *
     * @return Lista de todas as ComercioRaioViasDTO com distâncias.
     */
    @GetMapping("/distancias")
    public List<ComercioRaioViasDTO> getAllViasDistancia() {
        return service.findAllViasDistancia();
    }
}

