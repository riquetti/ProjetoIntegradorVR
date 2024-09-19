package com.riquetti.ProjetoIntegrador.controller;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioViasDTO;
import com.riquetti.ProjetoIntegrador.entity.ComercioRaioVias;
import com.riquetti.ProjetoIntegrador.service.ComercioRaioViasService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comercio-raio-vias")
public class ComercioRaioViasController {

    private final ComercioRaioViasService service;

    public ComercioRaioViasController(ComercioRaioViasService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public List<ComercioRaioViasDTO> getIdComercio(@PathVariable Long id) {
        return service.getIdVias(id);
    }

    @GetMapping
    public List<ComercioRaioViasDTO> getAllVias() {
        return service.getAllVias();
    }


    @GetMapping("/distancias/{id}")
    public List<ComercioRaioViasDTO> getIdComercioDistancia(
            @PathVariable Long id
    ) {
        // Chama o serviço passando apenas o id do comércio
        return service.findByComercioIdDistancia(id);
    }

    @GetMapping("/distancias")
    public List<ComercioRaioViasDTO> getAllViasDistancia() {
        return service.findAllViasDistancia();
    }


}
