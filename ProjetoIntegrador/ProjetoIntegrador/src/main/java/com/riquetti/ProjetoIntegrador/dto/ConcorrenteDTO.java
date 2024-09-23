package com.riquetti.ProjetoIntegrador.dto;

/**
 * Classe DTO  que representa os dados os dos comercios concorrentes
 * @param idComercio        O identificador único do comércio.
 * @param nome              O nome do comércio.
 * @param descricao         A descrição do comércio.
 * @param idTipoComercio    O identificador do tipo de comércio.
 * @param localizacao       Localização geográfica do comércio.
 */
public record ConcorrenteDTO(

        Long idComercio,
        String nome,
        String descricao,
        Long idTipoComercio,
        String localizacao

) {
}
