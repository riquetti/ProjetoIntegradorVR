package com.riquetti.ProjetoIntegrador.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador responsável por gerenciar as requisições da página de localização do comércio.
 */
@Controller
public class LocalizacaoComercioViewController {

    /**
     * Mapeia a requisição GET para a página de localização do comércio.
     *
     * @return O nome do arquivo HTML da página de localização do comércio, sem a extensão.
     */
    @GetMapping("/localizacao-comercio")
    public String localizacaoComercioPage() {
        return "localizacao_comercio"; // Nome do arquivo HTML sem a extensão .html
    }

}