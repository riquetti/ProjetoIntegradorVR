package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.ConcorrenteDTO;
import com.riquetti.ProjetoIntegrador.service.ConcorrenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gerenciar as operações relacionadas aos dados de outros comércios dentro do raio de ação.
 * Este controlador expõe endpoints para consultar os dados de comércios com base em diferentes parâmetros.
 */

@RestController
@RequestMapping("/api/concorrentes")
public class ConcorrenteController {
    private final ConcorrenteService service;

    public ConcorrenteController(ConcorrenteService service) {
        this.service = service;
    }

    /**
     * Obtém os concorrentes dentro de um raio especificado a partir de um comércio.
     *
     * @param idComercio      O ID do comércio, deve ser um inteiro maior que 0 e não nulo.
     * @param raioAcaoMetros  O raio em metros, deve ser um inteiro maior que 0 e não nulo.
     * @return Uma lista de DTOs de concorrentes dentro do raio especificado.
     */
    @GetMapping("/geral")
    public ResponseEntity<?> getConcorrentesByRaio(
            @RequestParam Long idComercio,
            @RequestParam Long raioAcaoMetros) {

        // Verifica se idComercio é nulo
        if (idComercio == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID do comércio não pode ser nulo.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Verifica se idComercio é maior que 0
        if (idComercio <= 0) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID do comércio deve ser maior que 0.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Verifica se raioAcaoMetros é nulo
        if (raioAcaoMetros == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O raio de ação em metros não pode ser nulo.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Verifica se raioAcaoMetros é maior que 0
        if (raioAcaoMetros <= 0) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O raio de ação em metros deve ser maior que 0.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Chama o serviço para buscar os concorrentes dentro do raio especificado
        List<ConcorrenteDTO> concorrentes = service.getConcorrentesByRaio(idComercio, raioAcaoMetros);
        return new ResponseEntity<>(concorrentes, HttpStatus.OK);
    }

    /**
     * Obtém os concorrentes utilizando o raio de ação associado ao comércio.
     *
     * @param idComercio O ID do comércio, deve ser um inteiro maior que 0 e não nulo.
     * @return Uma lista de DTOs de concorrentes encontrados.
     */
    @GetMapping("/raio-from-comercio/{idComercio}")
    public ResponseEntity<?> findByRaioFromComercio(@PathVariable Long idComercio) {
        // Verifica se idComercio é nulo
        if (idComercio == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID do comércio não pode ser nulo.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Verifica se idComercio é maior que 0
        if (idComercio <= 0) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID do comércio deve ser maior que 0.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Chama o serviço para buscar os concorrentes pelo raio de ação do comércio
        List<ConcorrenteDTO> concorrentes = service.findByRaioFromComercio(idComercio);
        return new ResponseEntity<>(concorrentes, HttpStatus.OK);
    }
}
