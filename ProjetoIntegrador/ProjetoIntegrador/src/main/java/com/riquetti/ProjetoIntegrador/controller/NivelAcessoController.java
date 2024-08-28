package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.NivelAcessoDTO;
import com.riquetti.ProjetoIntegrador.service.NivelAcessoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nivel-acesso")
public class NivelAcessoController {

    private final NivelAcessoService nivelAcessoService;

    public NivelAcessoController(NivelAcessoService nivelAcessoService) {
        this.nivelAcessoService = nivelAcessoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NivelAcessoDTO> getById(@PathVariable Long id) {
        NivelAcessoDTO nivelAcesso = nivelAcessoService.findById(id);
        return ResponseEntity.ok(nivelAcesso);
    }

    @GetMapping
    public ResponseEntity<List<NivelAcessoDTO>> getAll() {
        List<NivelAcessoDTO> niveisAcesso = nivelAcessoService.findAll();
        return ResponseEntity.ok(niveisAcesso);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody NivelAcessoDTO nivelAcessoDTO) {
        nivelAcessoService.save(nivelAcessoDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody NivelAcessoDTO nivelAcessoDTO) {
        nivelAcessoDTO = new NivelAcessoDTO(id, nivelAcessoDTO.nivelAcesso());
        nivelAcessoService.update(nivelAcessoDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        nivelAcessoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
