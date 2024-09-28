package com.riquetti.ProjetoIntegrador.dto;

import java.math.BigDecimal;

/**
 * Classe DTO  que representa os dados de um comércio.
 *
 * @param idComercio        O identificador único do comércio.
 * @param nome              O nome do comércio.
 * @param descricao         A descrição do comércio.
 * @param idTipoComercio    O identificador do tipo de comércio.
 * @param raioAcaoMetros    Raio de ação em metros (área de influência do comércio).
 *
 * @param localizacao       A localização do comércio em formato Well-Known Text (WKT) "POINT(-47.3990964 -22.5692409)".
 */

public record LocalizacaoComercioDTO(

        Long idComercio,
        String nome,
        String descricao,
        Long idTipoComercio,
        BigDecimal raioAcaoMetros,
        String localizacao

) {
}
