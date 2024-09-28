package com.riquetti.ProjetoIntegrador.dto;

/**
 * Classe DTO que representa um comércio e sua localização, incluindo informações sobre
 *  * o raio de ação e a contagem de comércios em diferentes distâncias.
 *  * Base de ruas e avenidas IBGE.
 *
 * @param idComercio        O identificador único do comércio.
 * @param nome              O nome do comércio.
 * @param localizacaoTexto  A localização do comércio em formato Well-Known Text (WKT) "POINT(-47.3990964 -22.5692409)".
 * @param raioAcaoMetros    Raio de ação em metros (área de influência do comércio).
 *                          Define o alcance em metros ao redor do comércio para análises geoespaciais.
 * @param total200m         Contagem de vias e avenidas dentro de um raio de 200 metros.
 * @param total500m         Contagem de vias e avenidas dentro de um raio de 500 metros.
 * @param total1km          Contagem de vias e avenidas dentro de um raio de 1 km.
 * @param total1_5km        Contagem de vias e avenidas dentro de um raio de 1,5 km.
 * @param total2km          Contagem de vias e avenidas dentro de um raio de 2 km.
 * @param totalRaioAcaoMetros Contagem de vias e avenidas dentro do raio de ação específico do comércio.
 */
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