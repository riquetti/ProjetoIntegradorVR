package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.LocalizacaoComercioDTO;
import com.riquetti.ProjetoIntegrador.service.LocalizacaoComercioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/localizacao-comercios")
public class LocalizacaoComercioController {

    private final LocalizacaoComercioService service;

    public LocalizacaoComercioController(LocalizacaoComercioService service) {
        this.service = service;
    }

    @GetMapping("/{idComercio}")
    public ResponseEntity<LocalizacaoComercioDTO> getLocalizacaoComercio(@PathVariable Long idComercio) {
        LocalizacaoComercioDTO dto = service.getLocalizacaoComercioById(idComercio);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<LocalizacaoComercioDTO>> getAllLocalizacaoComercios() {
        List<LocalizacaoComercioDTO> dtos = service.getAllLocalizacaoComercios();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<Void> createLocalizacaoComercio(@RequestBody LocalizacaoComercioDTO dto) {
        service.createLocalizacaoComercio(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{idComercio}")
    public ResponseEntity<Void> updateLocalizacaoComercio(@PathVariable Long idComercio, @RequestBody LocalizacaoComercioDTO dto) {
        if (!idComercio.equals(dto.idComercio())) {
            return ResponseEntity.badRequest().build();
        }
        service.updateLocalizacaoComercio(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idComercio}")
    public ResponseEntity<Void> deleteLocalizacaoComercio(@PathVariable Long idComercio) {
        service.deleteLocalizacaoComercio(idComercio);
        return ResponseEntity.noContent().build();
    }

}
