package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.TipoComercioDTO;
import com.riquetti.ProjetoIntegrador.entity.TipoComercio;
import com.riquetti.ProjetoIntegrador.service.TipoComercioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-comercio")
public class TipoComercioController {

    private final TipoComercioService tipoComercioService;

    public TipoComercioController(TipoComercioService tipoComercioService) {
        this.tipoComercioService = tipoComercioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoComercioDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoComercioService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<TipoComercioDTO>> getAll() {
        return ResponseEntity.ok(tipoComercioService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TipoComercioDTO tipoComercioDTO) {
        tipoComercioService.save(tipoComercioDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody TipoComercioDTO tipoComercioDTO) {
        tipoComercioDTO = new TipoComercioDTO(id, tipoComercioDTO.descricao());
        tipoComercioService.update(tipoComercioDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoComercioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
