package com.riquetti.ProjetoIntegrador.dto;

public record UsuarioDTO(

        Integer id,
        String nome,
        String email,
        String senha,
        Integer idNivelAcesso,
        Boolean ativo

) {
}
