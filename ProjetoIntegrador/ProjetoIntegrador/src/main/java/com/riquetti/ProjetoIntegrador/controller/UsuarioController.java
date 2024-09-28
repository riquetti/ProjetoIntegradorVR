package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.UsuarioDTO;
import com.riquetti.ProjetoIntegrador.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável pela gestão dos usuários da aplicação.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Obtém um usuário pelo seu ID.
     *
     * @param id O ID do usuário (deve ser um inteiro maior que 0 e não nulo).
     * @return Uma resposta contendo o usuário correspondente ao ID fornecido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("ID deve ser um número maior que 0.");
        }

        UsuarioDTO usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        return ResponseEntity.ok(usuario);
    }

    /**
     * Obtém todos os usuários.
     *
     * @return Uma lista de DTOs representando todos os usuários disponíveis.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    /**
     * Cria um novo usuário.
     *
     * @param usuarioDTO O DTO que representa o usuário a ser criado.
     * @return Uma resposta indicando a criação bem-sucedida.
     */
    @PostMapping
    public ResponseEntity<String> create(@RequestBody UsuarioDTO usuarioDTO) {
        // Validação do DTO
        if (usuarioDTO == null ||
                usuarioDTO.nome() == null || usuarioDTO.nome().isEmpty() || usuarioDTO.nome().length() > 100 ||
                usuarioDTO.email() == null || usuarioDTO.email().isEmpty() || usuarioDTO.email().length() > 100 ||
                usuarioDTO.senha() == null || usuarioDTO.senha().isEmpty() || usuarioDTO.senha().length() > 255 ||
                usuarioDTO.idNivelAcesso() == null || usuarioDTO.idNivelAcesso() <= 0) {
            return ResponseEntity.badRequest().body("Dados inválidos para criação do usuário.");
        }

        usuarioService.save(usuarioDTO);
        return ResponseEntity.status(201).body("Usuário criado com sucesso.");
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param id O ID do usuário a ser atualizado (deve ser um inteiro maior que 0 e não nulo).
     * @param usuarioDTO O DTO que contém as informações atualizadas do usuário.
     * @return Uma resposta indicando a atualização bem-sucedida.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("ID deve ser um número maior que 0.");
        }

        if (usuarioDTO == null ||
                usuarioDTO.nome() == null || usuarioDTO.nome().isEmpty() || usuarioDTO.nome().length() > 100 ||
                usuarioDTO.email() == null || usuarioDTO.email().isEmpty() || usuarioDTO.email().length() > 100 ||
                usuarioDTO.senha() == null || usuarioDTO.senha().isEmpty() || usuarioDTO.senha().length() > 255 ||
                usuarioDTO.idNivelAcesso() == null || usuarioDTO.idNivelAcesso() <= 0) {
            return ResponseEntity.badRequest().body("Dados inválidos para atualização do usuário.");
        }

        usuarioDTO = new UsuarioDTO(id, usuarioDTO.nome(), usuarioDTO.email(), usuarioDTO.senha(), usuarioDTO.idNivelAcesso(), usuarioDTO.ativo());
        usuarioService.update(usuarioDTO);
        return ResponseEntity.ok("Usuário atualizado com sucesso.");
    }

    /**
     * Remove um usuário pelo seu ID.
     *
     * @param id O ID do usuário a ser removido (deve ser um inteiro maior que 0 e não nulo).
     * @return Uma resposta indicando a remoção bem-sucedida.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("ID deve ser um número maior que 0.");
        }

        usuarioService.delete(id);
        return ResponseEntity.ok("Usuário removido com sucesso.");
    }
}
