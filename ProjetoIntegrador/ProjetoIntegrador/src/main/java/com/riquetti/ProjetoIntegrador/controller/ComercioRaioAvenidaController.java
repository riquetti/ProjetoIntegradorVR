package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioAvenidaDTO;
import com.riquetti.ProjetoIntegrador.service.ComercioRaioAvenidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api0/comercio-raio-avenida")
public class ComercioRaioAvenidaController {

    private final ComercioRaioAvenidaService service;

    public ComercioRaioAvenidaController(ComercioRaioAvenidaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ComercioRaioAvenidaDTO>> getAllComercioRaioAvenida() {
        return ResponseEntity.ok(service.findAll());
    }

    // Endpoint para buscar dados por id_comercio
    @GetMapping("/{idComercio}")
    public List<ComercioRaioAvenidaDTO> getComercioById(@PathVariable Long idComercio) {
        return service.getComerciosById(idComercio);
    }

    @GetMapping("/comercio/{idComercio}/raio/{raioAcaoMetros}")
    public ResponseEntity<ComercioRaioAvenidaDTO> getComercioByIdAndRaio(
            @PathVariable Long idComercio,
            @PathVariable Long raioAcaoMetros) {
        ComercioRaioAvenidaDTO dto = service.getComercioByIdAndRaio(idComercio, raioAcaoMetros);
        return ResponseEntity.ok(dto);
    }

    // Endpoint para buscar registros filtrados por localização
    @GetMapping("/ponto-raio-avenida")
    public ResponseEntity<List<ComercioRaioAvenidaDTO>> getPontoRaioAvenida(@RequestParam String localizacaoTexto) {
        return ResponseEntity.ok(service.getPontoRaioAvenida(localizacaoTexto));
    }

    // Endpoint para buscar registros filtrados por localização e raio de ação
    @GetMapping("/localizacao-raio-avenida")
    public List<ComercioRaioAvenidaDTO> getLocalizacaoRaioAvenida(
            @RequestParam String localizacaoTexto,
            @RequestParam double raioAcaopersolizado) {
        return service.getLocalizacaoRaioAvenida(localizacaoTexto, raioAcaopersolizado);
    }

}
