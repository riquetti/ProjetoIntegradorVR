package com.riquetti.ProjetoIntegrador.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocalizacaoComercioViewController {

    @GetMapping("/localizacao-comercio")
    public String localizacaoComercioPage() {
        return "localizacao_comercio"; // Nome do arquivo HTML sem a extensão .html
    }

}
