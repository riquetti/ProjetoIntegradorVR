package com.riquetti.ProjetoIntegrador.dto;

import java.math.BigDecimal;

/**
 * Classe DTO  que representa os dados de um comércio
 *
 * @param idComercio        O identificador único do comércio.
 * @param nome              O nome do comércio.
 * @param descricao         A descrição do comércio.
 * @param idTipoComercio    O identificador do tipo de comércio.
 * @param raioAcaoMetros    O raio de ação em metros definido para o comércio.
 * @param localizacao       Localização geográfica do comércio.
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
