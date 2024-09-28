package com.riquetti.ProjetoIntegrador.dto;

/**
 * Classe DTO  que representa a busca os dos comercios concorrentes.
 * Buscar os comercios dentro do raio de de ação.
 * @param idComercio        O identificador único do comércio.
 * @param nome              O nome do comércio.
 * @param descricao         A descrição do comércio.
 * @param idTipoComercio    O identificador do tipo de comércio.
 * @param localizacao       A localização do comércio em formato Well-Known Text (WKT) "POINT(-47.3990964 -22.5692409)".
 */
public record ConcorrenteDTO(

        Long idComercio,
        String nome,
        String descricao,
        Long idTipoComercio,
        String localizacao

) {
}
