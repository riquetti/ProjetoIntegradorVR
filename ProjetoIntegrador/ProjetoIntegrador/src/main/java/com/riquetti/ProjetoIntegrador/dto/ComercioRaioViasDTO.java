package com.riquetti.ProjetoIntegrador.dto;

import java.math.BigDecimal;

public record ComercioRaioViasDTO(

        Long idComercio,
        String nome,
        String descricao,
        Long raioAcaoMetros,
        BigDecimal comprimentoTotalPonderado,
        BigDecimal comprimento500,
        BigDecimal comprimento1000,
        BigDecimal comprimento1500,
        BigDecimal comprimento2000,
        BigDecimal comprimento2500,
        String acessibilidade,
        String localizacao

) {
}
