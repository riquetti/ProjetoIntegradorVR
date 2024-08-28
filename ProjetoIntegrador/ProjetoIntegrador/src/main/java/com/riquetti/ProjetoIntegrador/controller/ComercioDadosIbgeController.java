package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.ComercioDadosIbgeDTO;
import com.riquetti.ProjetoIntegrador.service.ComercioDadosIbgeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comercios-dados")
public class ComercioDadosIbgeController {
    private final ComercioDadosIbgeService service;

    public ComercioDadosIbgeController(ComercioDadosIbgeService service) {
        this.service = service;
    }

    @GetMapping("/{idComercio}")
    public List<ComercioDadosIbgeDTO> getLocalizacaoComercio(@PathVariable Long idComercio) {
        return service.getLocalizacaoComercioById(idComercio);
    }


    @GetMapping
    public List<ComercioDadosIbgeDTO> getAllLocalizacaoComercio() {
        return service.getAllLocalizacaoComercio();
    }

    // Endpoint para obter localização do comércio por ID com filtro opcional por raio de ação
    @GetMapping("raio/{idComercio}")
    public List<ComercioDadosIbgeDTO> getLocalizacaoComercioByIdWithRaio(
            @PathVariable Long idComercio,
            @RequestParam(value = "raioAcaoMetros", required = false) Long raioAcaoMetros) {
        return service.getLocalizacaoComercioByIdWithRaio(idComercio, raioAcaoMetros);
    }

    // Endpoint para obter todas as localizações do comércio com filtro opcional por raio de ação
    @GetMapping("raio")
    public List<ComercioDadosIbgeDTO> getAllLocalizacaoComercioWithRaio(
            @RequestParam(value = "raioAcaoMetros", required = false) Long raioAcaoMetros) {
        return service.getAllLocalizacaoComercioWithRaio(raioAcaoMetros);
    }

}
