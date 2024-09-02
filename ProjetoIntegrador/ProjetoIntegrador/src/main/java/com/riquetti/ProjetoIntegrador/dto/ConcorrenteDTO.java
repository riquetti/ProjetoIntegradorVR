package com.riquetti.ProjetoIntegrador.dto;

public record ConcorrenteDTO(

        Long idComercio,
        String nome,
        String descricao,
        Long idTipoComercio,
        String localizacao

) {
}
