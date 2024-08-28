package com.riquetti.ProjetoIntegrador.dto;

import java.math.BigDecimal;

public record ComercioDadosIbgeDTO(

        Long idComercio,
        String nome,
        String localizacaoTexto,
        Long raioAcaoMetros,
        BigDecimal rendaMediaIbge2010

) {

}
