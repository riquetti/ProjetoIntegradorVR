package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.NivelAcessoDTO;
import com.riquetti.ProjetoIntegrador.service.NivelAcessoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável pela gestão dos níveis de acesso da aplicação.
 */
@RestController
@RequestMapping("/api/nivel-acesso")
public class NivelAcessoController {

    private final NivelAcessoService nivelAcessoService;

    public NivelAcessoController(NivelAcessoService nivelAcessoService) {
        this.nivelAcessoService = nivelAcessoService;
    }

    /**
     * Obtém um nível de acesso pelo seu ID.
     *
     * @param id O ID do nível de acesso (deve ser um inteiro maior que 0 e não nulo).
     * @return Uma resposta contendo o nível de acesso correspondente ao ID fornecido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("ID deve ser um número maior que 0.");
        }

        NivelAcessoDTO nivelAcesso = nivelAcessoService.findById(id);
        if (nivelAcesso == null) {
            return ResponseEntity.status(404).body("Nível de acesso não encontrado.");
        }

        return ResponseEntity.ok(nivelAcesso);
    }

    /**
     * Obtém todos os níveis de acesso.
     *
     * @return Uma lista de DTOs representando todos os níveis de acesso disponíveis.
     */
    @GetMapping
    public ResponseEntity<List<NivelAcessoDTO>> getAll() {
        List<NivelAcessoDTO> niveisAcesso = nivelAcessoService.findAll();
        return ResponseEntity.ok(niveisAcesso);
    }

    /**
     * Cria um novo nível de acesso.
     *
     * @param nivelAcessoDTO O DTO que representa o nível de acesso a ser criado (o campo nivelAcesso não pode ser nulo e deve ter no máximo 255 caracteres).
     * @return Uma resposta indicando a criação bem-sucedida.
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestBody NivelAcessoDTO nivelAcessoDTO) {
        if (nivelAcessoDTO == null || nivelAcessoDTO.nivelAcesso() == null || nivelAcessoDTO.nivelAcesso().isEmpty() ||
                nivelAcessoDTO.nivelAcesso().length() > 255) {
            return ResponseEntity.badRequest().body("Dados inválidos para criação do nível de acesso.");
        }

        nivelAcessoService.save(nivelAcessoDTO);
        return ResponseEntity.status(201).body("Nível de acesso criado com sucesso.");
    }

    /**
     * Atualiza um nível de acesso existente.
     *
     * @param id             O ID do nível de acesso a ser atualizado (deve ser um inteiro maior que 0 e não nulo).
     * @param nivelAcessoDTO O DTO que contém as informações atualizadas do nível de acesso.
     * @return Uma resposta indicando a atualização bem-sucedida.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody NivelAcessoDTO nivelAcessoDTO) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("ID deve ser um número maior que 0.");
        }

        if (nivelAcessoDTO == null || nivelAcessoDTO.nivelAcesso() == null || nivelAcessoDTO.nivelAcesso().isEmpty() ||
                nivelAcessoDTO.nivelAcesso().length() > 255) {
            return ResponseEntity.badRequest().body("Dados inválidos para atualização do nível de acesso.");
        }

        nivelAcessoDTO = new NivelAcessoDTO(id, nivelAcessoDTO.nivelAcesso());
        nivelAcessoService.update(nivelAcessoDTO);
        return ResponseEntity.ok("Nível de acesso atualizado com sucesso.");
    }

    /**
     * Remove um nível de acesso pelo seu ID.
     *
     * @param id O ID do nível de acesso a ser removido (deve ser um inteiro maior que 0 e não nulo).
     * @return Uma resposta indicando a remoção bem-sucedida.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("ID deve ser um número maior que 0.");
        }

        nivelAcessoService.delete(id);
        return ResponseEntity.ok("Nível de acesso removido com sucesso.");
    }
}
