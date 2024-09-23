package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioAvenidaDTO;
import com.riquetti.ProjetoIntegrador.service.ComercioRaioAvenidaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gerenciar as operações relacionadas aos dados de localização dos comércios vias próximas.
 * Este controlador expõe endpoints para consultar os dados de comércios com base em diferentes parâmetros.
 */
@RestController
@RequestMapping("/api0/comercio-raio-avenida")
public class ComercioRaioAvenidaController {

    /**
     * Serviço para operações relacionadas a comércios e raios de ação.
     */
    private final ComercioRaioAvenidaService service;

    /**
     * Construtor do controlador que recebe o serviço.
     *
     * @param service O serviço responsável por gerenciar as operações de comércios.
     */
    public ComercioRaioAvenidaController(ComercioRaioAvenidaService service) {
        this.service = service;
    }

    /**
     * Recupera todos os comércios com raio de ação.
     *
     * @return Resposta HTTP contendo uma lista de DTOs de comércios.
     */
    @GetMapping
    public ResponseEntity<List<ComercioRaioAvenidaDTO>> getAllComercioRaioAvenida() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Recupera comércios pelo ID.
     *
     * @param idComercioStr ID do comércio a ser buscado.
     * @return Lista de DTOs de comércios correspondentes ao ID.
     * @throws IllegalArgumentException se o ID do comércio for nulo ou menor ou igual a 0.
     *                                  Retorna uma mensagem de erro personalizada.
     */
    @GetMapping("/{idComercio}")
    public ResponseEntity<?> getComercioById(@PathVariable("idComercio") String idComercioStr) {

        // Verifica se idComercio está nulo ou vazio
        if (idComercioStr == null || idComercioStr.trim().isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID do comércio não pode ser nulo ou vazio.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Long idComercio = Long.parseLong(idComercioStr);

            // Verifica se o ID é maior que 0
            if (idComercio <= 0) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "O ID do comércio deve ser maior que 0.");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            // Chama o serviço para buscar o comércio
            List<ComercioRaioAvenidaDTO> result = service.getComerciosById(idComercio);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (NumberFormatException e) {
            // Trata erro de formato inválido para o ID
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID do comércio deve ser um número válido.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Recupera um comércio pelo ID e raio de ação.
     *
     * @param idComercio      ID do comércio a ser buscado. Deve ser um valor numérico maior que 0.
     * @param raioAcaoMetros  Raio de ação em metros. Deve ser um valor numérico maior que 0.
     * @return Resposta HTTP contendo o DTO do comércio correspondente ou uma mensagem de erro.
     */
    @GetMapping("/comercio/{idComercio}/raio/{raioAcaoMetros}")
    public ResponseEntity<?> getComercioByIdAndRaio(
            @PathVariable String idComercio, // Alterado para String para validação de entrada
            @PathVariable String raioAcaoMetros) { // Alterado para String para validação de entrada

        // Validações para o ID do comércio
        try {
            Long comercioId = Long.parseLong(idComercio);
            if (comercioId <= 0) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "O ID do comércio deve ser um número maior que 0.");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            // Validações para o raio de ação
            Double raioMetros = Double.parseDouble(raioAcaoMetros);
            if (raioMetros <= 0) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "O valor do raio de ação deve ser maior que 0.");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            // Chama o serviço caso os valores sejam válidos
            ComercioRaioAvenidaDTO dto = service.getComercioByIdAndRaio(comercioId, raioMetros);
            return ResponseEntity.ok(dto);

        } catch (NumberFormatException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O ID do comércio e o valor do raio de ação devem ser números válidos.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Recupera comércios filtrados por localização textual em formato POINT.
     *
     * @param localizacaoTexto Texto representando a localização em formato POINT. Exemplo: POINT(-47.4042319 -22.561447).
     * @return Lista de DTOs de comércios correspondentes à localização.
     * @throws IllegalArgumentException se o formato do ponto de texto for inválido.
     */
    @GetMapping("/ponto-raio-avenida")
    public ResponseEntity<?> getPontoRaioAvenida(@RequestParam String localizacaoTexto) {
        // Valida se a localização está no formato esperado
        if (!isValidPointFormat(localizacaoTexto)) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O formato do ponto de texto deve ser 'POINT(x y)', onde x e y são coordenadas válidas.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        List<ComercioRaioAvenidaDTO> result = service.getPontoRaioAvenida(localizacaoTexto);
        return ResponseEntity.ok(result);
    }

    /**
     * Recupera comércios filtrados por localização e raio de ação.
     *
     * @param localizacaoTexto Texto representando a localização em formato POINT. Exemplo: POINT(-47.4042319 -22.561447).
     * @param raioAcaopersolizado Raio de ação em metros. Deve ser um valor maior que 0.
     * @return Lista de DTOs de comércios correspondentes à localização e raio.
     */

    @GetMapping("/localizacao-raio-avenida")
    public ResponseEntity<?> getLocalizacaoRaioAvenida(
            @RequestParam String localizacaoTexto,
            @RequestParam String raioAcaopersolizado) {

        // Valida se a localização está no formato esperado
        if (!isValidPointFormat(localizacaoTexto)) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O formato do ponto de texto deve ser 'POINT(x y)', onde x e y são coordenadas válidas.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Valida se o raio é um número válido e maior que 0
        double raio;
        try {
            raio = Double.parseDouble(raioAcaopersolizado);
            if (raio <= 0) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "O valor do raio deve ser maior que 0.");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "O valor do raio deve ser um número válido.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        List<ComercioRaioAvenidaDTO> result = service.getLocalizacaoRaioAvenida(localizacaoTexto, raio);
        return ResponseEntity.ok(result);
    }

    /**
     * Verifica se o texto está no formato válido de POINT.
     *
     * @param point Texto a ser verificado.
     * @return true se o formato for válido, false caso contrário.
     */
    private boolean isValidPointFormat(String point) {
        return point != null && point.matches("^POINT\\(-?\\d+\\.\\d+ -?\\d+\\.\\d+\\)$");
    }

}
