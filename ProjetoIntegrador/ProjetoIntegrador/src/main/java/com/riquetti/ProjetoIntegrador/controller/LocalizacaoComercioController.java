package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.LocalizacaoComercioDTO;
import com.riquetti.ProjetoIntegrador.service.LocalizacaoComercioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Controlador para gerenciar as operações relacionadas aos dados de comércios.
 * Este controlador expõe endpoints para consulta de dados dos comercios.
 */
@RestController
@RequestMapping("/api/localizacao-comercios")
public class LocalizacaoComercioController {

    private final LocalizacaoComercioService service;

    public LocalizacaoComercioController(LocalizacaoComercioService service) {
        this.service = service;
    }

    /**
     * Obtém os dados cadastrados de um comércio pelo ID especificado.
     *
     * @param idComercio O ID do comércio, deve ser um inteiro maior que 0 e não nulo.
     * @return A localização do comércio correspondente ao ID especificado.
     */
    @GetMapping("/{idComercio}")
    public ResponseEntity<LocalizacaoComercioDTO> getLocalizacaoComercio(@PathVariable @Min(1) Long idComercio) {
        LocalizacaoComercioDTO dto = service.getLocalizacaoComercioById(idComercio);
        return ResponseEntity.ok(dto);
    }

    /**
     * Obtém todas as informações de comércios.
     *
     * @return Uma lista de localizações de comércios.
     */
    @GetMapping
    public ResponseEntity<List<LocalizacaoComercioDTO>> getAllLocalizacaoComercios() {
        List<LocalizacaoComercioDTO> dtos = service.getAllLocalizacaoComercios();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Armazena os dados de criação do comércio.
     *
     * @param dto O DTO contendo os dados da nova localização de comércio, que não pode ser nulo.
     * @return Uma resposta indicando que a localização foi criada com sucesso.
     */
    @PostMapping
    public ResponseEntity<Void> createLocalizacaoComercio(@RequestBody @Valid LocalizacaoComercioDTO dto) {
        validateLocalizacaoComercio(dto);
        service.createLocalizacaoComercio(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Atualiza os dados de um comércio existente.
     *
     * @param idComercio O ID do comércio a ser atualizado, deve ser um inteiro maior que 0 e não nulo.
     * @param dto        O DTO contendo os dados atualizados do comércio.
     * @return Uma resposta indicando que a localização foi atualizada com sucesso.
     */
    @PutMapping("/{idComercio}")
    public ResponseEntity<Void> updateLocalizacaoComercio(@PathVariable @Min(1) Long idComercio, @RequestBody @Valid LocalizacaoComercioDTO dto) {
        if (!idComercio.equals(dto.idComercio())) {
            return ResponseEntity.badRequest().build();
        }
        validateLocalizacaoComercio(dto);
        service.updateLocalizacaoComercio(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * Remove os dados de um comércio pelo ID especificado.
     *
     * @param idComercio O ID do comércio a ser removido, deve ser um inteiro maior que 0 e não nulo.
     * @return Uma resposta indicando que a localização foi removida com sucesso.
     */
    @DeleteMapping("/{idComercio}")
    public ResponseEntity<Void> deleteLocalizacaoComercio(@PathVariable @Min(1) Long idComercio) {
        service.deleteLocalizacaoComercio(idComercio);
        return ResponseEntity.noContent().build();
    }

    /**
     * Valida os dados do DTO do comércio.
     *
     * @param dto O DTO a ser validado.
     */
    private void validateLocalizacaoComercio(LocalizacaoComercioDTO dto) {
        if (dto.nome() == null || dto.nome().isEmpty() || dto.nome().length() > 255) {
            throw new IllegalArgumentException("O nome não pode ser nulo, vazio ou exceder 255 caracteres.");
        }
        if (dto.idTipoComercio() == null || dto.idTipoComercio() <= 0) {
            throw new IllegalArgumentException("O ID do tipo de comércio deve ser um inteiro maior que 0 e não nulo.");
        }
        if (dto.raioAcaoMetros() == null || dto.raioAcaoMetros().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O raio de ação deve ser um número maior que 0 e não nulo.");
        }
        if (dto.localizacao() == null || !isValidPointFormat(dto.localizacao())) {
            throw new IllegalArgumentException("A localização deve estar no formato POINT(lon lat), por exemplo, POINT(-47.3990964 -22.5692409).");
        }
    }

    /**
     * Verifica se a string da localização está no formato válido de POINT.
     *
     * @param point A string representando a localização.
     * @return true se o formato estiver correto, caso contrário, false.
     */
    private boolean isValidPointFormat(String point) {
        String pointPattern = "^POINT\\s*\\(-?\\d+(\\.\\d+)?\\s+-?\\d+(\\.\\d+)?\\)$";
        return Pattern.matches(pointPattern, point);
    }
}
