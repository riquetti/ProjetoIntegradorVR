package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.UsuarioDTO;
import com.riquetti.ProjetoIntegrador.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.save(usuarioDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        usuarioDTO = new UsuarioDTO(id, usuarioDTO.nome(), usuarioDTO.email(), usuarioDTO.senha(), usuarioDTO.idNivelAcesso(), usuarioDTO.ativo());
        usuarioService.update(usuarioDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
