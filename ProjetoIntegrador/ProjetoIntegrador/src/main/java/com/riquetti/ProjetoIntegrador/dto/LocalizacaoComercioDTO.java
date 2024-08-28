package com.riquetti.ProjetoIntegrador.dto;

import java.math.BigDecimal;

public record LocalizacaoComercioDTO(

        Long idComercio,
        String nome,
        String descricao,
        Long idTipoComercio,
        BigDecimal raioAcaoMetros,
        String localizacao

) {
}
