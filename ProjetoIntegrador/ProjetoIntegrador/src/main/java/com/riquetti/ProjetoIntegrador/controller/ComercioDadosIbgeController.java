package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.ComercioDadosIbgeDTO;
import com.riquetti.ProjetoIntegrador.service.ComercioDadosIbgeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gerenciar as operações relacionadas aos dados de localização dos comércios.
 */
@RestController
@RequestMapping("/api/comercios-dados")
public class ComercioDadosIbgeController {

    private final ComercioDadosIbgeService service;

    public ComercioDadosIbgeController(ComercioDadosIbgeService service) {
        this.service = service;
    }

    /**
     * Obtém as localizações de um comércio específico baseado no ID.
     *
     * @param idComercio ID do comércio que se deseja buscar.
     * @return Lista de localizações do comércio em formato DTO.
     */
    @GetMapping("/{idComercio}")
    public ResponseEntity<?> getLocalizacaoComercio(@PathVariable @Valid Long idComercio) {
        if (idComercio <= 0) {
            return badRequest("O ID do comércio deve ser maior que 0.");
        }

        List<ComercioDadosIbgeDTO> result = service.getLocalizacaoComercioById(idComercio);

        if (result.isEmpty()) {
            return notFound("Comércio não encontrado para o ID fornecido.");
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Obtém as localizações de todos os comércios.
     */
    @GetMapping
    public ResponseEntity<List<ComercioDadosIbgeDTO>> getAllLocalizacaoComercio() {
        List<ComercioDadosIbgeDTO> result = service.getAllLocalizacaoComercio();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Obtém as localizações de um comércio específico com um filtro opcional de raio de ação.
     */
    @GetMapping("raio/{idComercio}")
    public ResponseEntity<?> getLocalizacaoComercioByIdWithRaio(
            @PathVariable @Valid Long idComercio,
            @RequestParam(value = "raioAcaoMetros", required = false) String raioAcaoMetrosStr) {

        if (idComercio <= 0) {
            return badRequest("O ID do comércio deve ser maior que 0.");
        }

        if (isInvalidRaio(raioAcaoMetrosStr)) {
            return badRequest("O valor do raio deve ser um número válido e maior que 0.");
        }

        Double raioAcaoMetros = Double.parseDouble(raioAcaoMetrosStr);
        List<ComercioDadosIbgeDTO> result = service.getLocalizacaoComercioByIdWithRaio(idComercio, raioAcaoMetros);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Obtém as localizações de todos os comércios com um filtro opcional de raio de ação.
     */
    @GetMapping("raio")
    public ResponseEntity<?> getAllLocalizacaoComercioWithRaio(
            @RequestParam(value = "raioAcaoMetros", required = false) String raioAcaoMetrosStr) {

        if (isInvalidRaio(raioAcaoMetrosStr)) {
            return badRequest("O valor do raio deve ser um número válido e maior que 0.");
        }

        Double raioAcaoMetros = Double.parseDouble(raioAcaoMetrosStr);
        List<ComercioDadosIbgeDTO> result = service.getAllLocalizacaoComercioWithRaio(raioAcaoMetros);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Obtém as localizações de um comércio baseado em um ponto e um raio de ação.
     */
    @GetMapping("/ponto-raio")
    public ResponseEntity<?> getLocalizacaoComercioByPontoAndRaio(
            @RequestParam("pontoTexto") String pontoTexto,
            @RequestParam("raioAcaoMetros") String raioAcaoMetrosStr) {

        if (pontoTexto == null || pontoTexto.trim().isEmpty()) {
            return badRequest("O ponto de texto não pode ser nulo ou vazio.");
        }

        if (!isValidLocationFormat(pontoTexto)) {
            return badRequest("O formato do ponto de texto é inválido.");
        }

        if (isInvalidRaio(raioAcaoMetrosStr)) {
            return badRequest("O valor do raio deve ser um número válido e maior que 0.");
        }

        Long raioAcaoMetros = Long.parseLong(raioAcaoMetrosStr);
        List<ComercioDadosIbgeDTO> result = service.getLocalizacaoComercioByPontoAndRaio(pontoTexto, raioAcaoMetros);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private boolean isValidLocationFormat(String pontoTexto) {
        return pontoTexto.matches("POINT\\s*\\(\\s*-?\\d+\\.\\d+\\s+-?\\d+\\.\\d+\\s*\\)");
    }

    private boolean isInvalidRaio(String raioAcaoMetrosStr) {
        try {
            Double raioAcaoMetros = Double.parseDouble(raioAcaoMetrosStr);
            return raioAcaoMetros <= 0;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private ResponseEntity<Map<String, String>> badRequest(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, String>> notFound(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
