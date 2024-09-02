package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.ConcorrenteDTO;
import com.riquetti.ProjetoIntegrador.service.ConcorrenteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/concorrentes")
public class ConcorrenteController {
    private final ConcorrenteService service;

    public ConcorrenteController(ConcorrenteService service) {
        this.service = service;
    }

    @GetMapping("/geral")
    public List<ConcorrenteDTO> getConcorrentesByRaio(
            @RequestParam Long idComercio,
            @RequestParam(value = "raioAcaoMetros") Long raioAcaoMetros) {
        return service.getConcorrentesByRaio(idComercio, raioAcaoMetros);
    }
}
