package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.service.IBGEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IBGEController {

    @Autowired
    private IBGEService ibgeService;

    @GetMapping("/malha/{municipioId}")
    public String getMalhaMunicipio(@PathVariable String municipioId) {
        // Chama o serviço para obter o GeoJSON da malha do município
        return ibgeService.getMalhaMunicipio(municipioId);
    }

}
