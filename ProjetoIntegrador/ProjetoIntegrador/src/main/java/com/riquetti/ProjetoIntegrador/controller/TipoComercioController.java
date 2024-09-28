package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.TipoComercioDTO;
import com.riquetti.ProjetoIntegrador.service.TipoComercioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável pela gestão dos tipos de comércio da aplicação.
 */
@RestController
@RequestMapping("/api/tipo-comercio")
public class TipoComercioController {

    private final TipoComercioService tipoComercioService;

    public TipoComercioController(TipoComercioService tipoComercioService) {
        this.tipoComercioService = tipoComercioService;
    }

    /**
     * Obtém um tipo de comércio pelo seu ID.
     *
     * @param id O ID do tipo de comércio (deve ser um inteiro maior que 0 e não nulo).
     * @return Uma resposta contendo o tipo de comércio correspondente ao ID fornecido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("ID deve ser um número maior que 0.");
        }

        TipoComercioDTO tipoComercio = tipoComercioService.findById(id);
        if (tipoComercio == null) {
            return ResponseEntity.status(404).body("Tipo de comércio não encontrado.");
        }

        return ResponseEntity.ok(tipoComercio);
    }

    /**
     * Obtém todos os tipos de comércio.
     *
     * @return Uma lista de DTOs representando todos os tipos de comércio disponíveis.
     */
    @GetMapping
    public ResponseEntity<List<TipoComercioDTO>> getAll() {
        return ResponseEntity.ok(tipoComercioService.findAll());
    }

    /**
     * Cria um novo tipo de comércio.
     *
     * @param tipoComercioDTO O DTO que representa o tipo de comércio a ser criado.
     * @return Uma resposta indicando a criação bem-sucedida.
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestBody TipoComercioDTO tipoComercioDTO) {
        // Validação do DTO
        if (tipoComercioDTO == null || tipoComercioDTO.descricao() == null || tipoComercioDTO.descricao().isEmpty() ||
                tipoComercioDTO.descricao().length() > 255) {
            return ResponseEntity.badRequest().body("Dados inválidos para criação do tipo de comércio.");
        }

        tipoComercioService.save(tipoComercioDTO);
        return ResponseEntity.status(201).body("Tipo de comércio criado com sucesso.");
    }

    /**
     * Atualiza um tipo de comércio existente.
     *
     * @param id O ID do tipo de comércio a ser atualizado (deve ser um inteiro maior que 0 e não nulo).
     * @param tipoComercioDTO O DTO que contém as informações atualizadas do tipo de comércio.
     * @return Uma resposta indicando a atualização bem-sucedida.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody TipoComercioDTO tipoComercioDTO) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("ID deve ser um número maior que 0.");
        }

        if (tipoComercioDTO == null || tipoComercioDTO.descricao() == null || tipoComercioDTO.descricao().isEmpty() ||
                tipoComercioDTO.descricao().length() > 255) {
            return ResponseEntity.badRequest().body("Dados inválidos para atualização do tipo de comércio."); // Mensagem personalizada
        }

        tipoComercioDTO = new TipoComercioDTO(id, tipoComercioDTO.descricao());
        tipoComercioService.update(tipoComercioDTO);
        return ResponseEntity.ok("Tipo de comércio atualizado com sucesso.");
    }

    /**
     * Remove um tipo de comércio pelo seu ID.
     *
     * @param id O ID do tipo de comércio a ser removido (deve ser um inteiro maior que 0 e não nulo).
     * @return Uma resposta indicando a remoção bem-sucedida.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("ID deve ser um número maior que 0.");
        }

        tipoComercioService.delete(id);
        return ResponseEntity.ok("Tipo de comércio removido com sucesso.");
    }
}
