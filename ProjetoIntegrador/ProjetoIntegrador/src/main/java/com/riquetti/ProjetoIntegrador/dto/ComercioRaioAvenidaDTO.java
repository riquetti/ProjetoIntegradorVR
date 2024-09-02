package com.riquetti.ProjetoIntegrador.dto;

public record ComercioRaioAvenidaDTO(

        Long idComercio,
        String nome,
        String localizacaoTexto,
        Long raioAcaoMetros,
        int total200m,
        int total500m,
        int total1km,
        int total1_5km,
        int total2km,
        int totalRaioAcaoMetros

) {
}
