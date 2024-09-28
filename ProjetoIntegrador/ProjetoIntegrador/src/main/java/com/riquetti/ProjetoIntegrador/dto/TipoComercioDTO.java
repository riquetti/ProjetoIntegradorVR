package com.riquetti.ProjetoIntegrador.dto;

/**
 * Classe DTO que representa o tipo de comércio.
 * @param idTipoComercio    O identificador único do tipo de comércio.
 * @param descricao         A descrição do tipo de comércio (por exemplo, "Supermercado", "Loja de Roupas", etc.).
 */
public record TipoComercioDTO(

        Long idTipoComercio,
        String descricao

) {
}
