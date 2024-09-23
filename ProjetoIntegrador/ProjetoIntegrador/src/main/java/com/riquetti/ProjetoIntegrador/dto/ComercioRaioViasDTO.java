package com.riquetti.ProjetoIntegrador.dto;

import java.math.BigDecimal;

/**
 * Classe DTO  que representa os dados de um comércio e
 * suas características relacionadas ao comprimento das vias em diferentes raios de ação.
 * Base OSM (open street map)
 *
 * @param idComercio                     O identificador único do comércio.
 * @param nome                           O nome do comércio.
 * @param descricao                      A descrição do comércio.
 * @param raioAcaoMetros                 O raio de ação em metros definido para o comércio.
 * @param comprimentoTotalPonderado      O comprimento total ponderado das vias ao redor do comércio,
 *                                       considera as vias (classe com peso diferentes para o tipo de via) dentro do raio de ação.
 * @param comprimento500                  O comprimento das vias dentro de um raio de 500 metros.
 * @param comprimento1000                 O comprimento das vias dentro de um raio de 1 km.
 * @param comprimento1500                 O comprimento das vias dentro de um raio de 1,5 km.
 * @param comprimento2000                 O comprimento das vias dentro de um raio de 2 km.
 * @param comprimento2500                 O comprimento das vias dentro de um raio de 2,5 km.
 * @param acessibilidade                  Classificação da informação sobre a acessibilidade do comércio.
 * @param localizacao                     Localização geográfica do comércio.
 */
public record ComercioRaioViasDTO(

        Long idComercio,
        String nome,
        String descricao,
        Long raioAcaoMetros,
        BigDecimal comprimentoTotalPonderado,
        BigDecimal comprimento500,
        BigDecimal comprimento1000,
        BigDecimal comprimento1500,
        BigDecimal comprimento2000,
        BigDecimal comprimento2500,
        String acessibilidade,
        String localizacao

) {
}
