package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.ComercioDadosIbgeDTO;
import com.riquetti.ProjetoIntegrador.exception.ResourceNotFoundException;
import com.riquetti.ProjetoIntegrador.service.ComercioDadosIbgeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gerenciar as operações relacionadas aos dados de localização dos comércios.
 * Este controlador expõe endpoints para consultar os dados de comércios com base em diferentes parâmetros.
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
     * @throws ResourceNotFoundException Se o comércio com o ID fornecido não for encontrado.
     */
    @GetMapping("/{idComercio}")
    public ResponseEntity<?> getLocalizacaoComercio(@PathVariable Long idComercio) {
        if (idComercio == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID do comércio não pode ser nulo.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (idComercio <= 0) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID do comércio deve ser maior que 0.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            List<ComercioDadosIbgeDTO> result = service.getLocalizacaoComercioById(idComercio);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Obtém as localizações de todos os comércios.
     *
     * @return Lista de localizações de todos os comércios em formato DTO.
     */
    @GetMapping
    public ResponseEntity<List<ComercioDadosIbgeDTO>> getAllLocalizacaoComercio() {
        List<ComercioDadosIbgeDTO> result = service.getAllLocalizacaoComercio();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Obtém as localizações de um comércio específico com um filtro opcional de raio de ação.
     *
     * @param idComercio ID do comércio que se deseja buscar.
     * @param raioAcaoMetrosStr Raio de ação em metros (como texto).
     * @return Lista de localizações do comércio em formato DTO.
     * @throws NumberFormatException Se o valor do raio não for um número válido.
     * @throws ResourceNotFoundException Se o comércio com o ID fornecido não for encontrado.
     */
    @GetMapping("raio/{idComercio}")
    public ResponseEntity<?> getLocalizacaoComercioByIdWithRaio(
            @PathVariable Long idComercio,
            @RequestParam(value = "raioAcaoMetros", required = false) String raioAcaoMetrosStr) {

        // Verifica se o ID do comércio é nulo ou menor ou igual a 0
        if (idComercio == null || idComercio <= 0) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID do comércio deve ser maior que 0.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Verifica se raioAcaoMetrosStr é nulo ou vazio
        if (raioAcaoMetrosStr == null || raioAcaoMetrosStr.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O valor do raio não pode ser nulo ou vazio.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Double raioAcaoMetros = Double.parseDouble(raioAcaoMetrosStr);

            // Verifica se o raio é menor ou igual a 0
            if (raioAcaoMetros <= 0) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "O valor do raio deve ser maior que 0.");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            List<ComercioDadosIbgeDTO> result = service.getLocalizacaoComercioByIdWithRaio(idComercio, raioAcaoMetros);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NumberFormatException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O valor do raio deve ser um número válido.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Obtém as localizações de todos os comércios com um filtro opcional de raio de ação.
     *
     * @param raioAcaoMetrosStr Raio de ação em metros (como texto).
     * @return Lista de localizações de todos os comércios em formato DTO.
     * @throws NumberFormatException Se o valor do raio não for um número válido.
     */
    @GetMapping("raio")
    public ResponseEntity<?> getAllLocalizacaoComercioWithRaio(
            @RequestParam(value = "raioAcaoMetros", required = false) String raioAcaoMetrosStr) {
        if (raioAcaoMetrosStr == null || raioAcaoMetrosStr.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O valor do raio não pode ser nulo ou vazio.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Double raioAcaoMetros = Double.parseDouble(raioAcaoMetrosStr);

            if (raioAcaoMetros <= 0) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "O valor do raio deve ser maior que 0.");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            List<ComercioDadosIbgeDTO> result = service.getAllLocalizacaoComercioWithRaio(raioAcaoMetros);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NumberFormatException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O valor do raio deve ser um número válido.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Obtém as localizações de um comércio baseado em um ponto e um raio de ação.
     *
     * @param pontoTexto Ponto de localização em formato texto (WKT).
     * @param raioAcaoMetrosStr Raio de ação em metros (como texto).
     * @return Lista de localizações do comércio em formato DTO.
     * @throws NumberFormatException Se o valor do raio não for um número válido.
     */
    @GetMapping("/ponto-raio")
    public ResponseEntity<?> getLocalizacaoComercioByPontoAndRaio(
            @RequestParam("pontoTexto") String pontoTexto,
            @RequestParam("raioAcaoMetros") String raioAcaoMetrosStr) {

        // Verifica se pontoTexto é nulo ou vazio
        if (pontoTexto == null || pontoTexto.trim().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ponto de texto não pode ser nulo ou vazio.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Adicione aqui uma validação adicional se necessário (por exemplo, formato de coordenadas)
        if (!isValidLocationFormat(pontoTexto)) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O formato do ponto de texto é inválido.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Long raioAcaoMetros = Long.parseLong(raioAcaoMetrosStr);
            if (raioAcaoMetros <= 0) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "O valor do raio deve ser maior que 0.");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            List<ComercioDadosIbgeDTO> result = service.getLocalizacaoComercioByPontoAndRaio(pontoTexto, raioAcaoMetros);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NumberFormatException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O valor do raio deve ser um número válido.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Método de exemplo para validar o formato do ponto (adapte conforme necessário)
    private boolean isValidLocationFormat(String pontoTexto) {
        // Exemplo simples de validação: verifique se é um formato WKT (Well-Known Text)
        // Você pode adaptar isso conforme o formato esperado
        return pontoTexto.matches("POINT\\s*\\(\\s*-?\\d+\\.\\d+\\s+-?\\d+\\.\\d+\\s*\\)");
    }


}


