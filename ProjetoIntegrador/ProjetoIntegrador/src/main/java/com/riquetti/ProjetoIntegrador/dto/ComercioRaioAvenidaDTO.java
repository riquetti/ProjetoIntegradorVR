package com.riquetti.ProjetoIntegrador.dto;

/**
 * Classe DTO que representa os dados de um comércio
 * e as contagens de vias e avenidas dentro de diferentes raios de ação.
 *
 * @param idComercio        O identificador único do comércio.
 * @param nome              O nome do comércio.
 * @param localizacaoTexto  A localização do comércio em formato de texto (WKT - Well-Known Text).
 * @param raioAcaoMetros    O raio de ação em metros definido para o comércio.
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