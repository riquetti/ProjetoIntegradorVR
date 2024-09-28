package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;


/**
 * Classe que representa um comércio e sua localização, incluindo informações sobre
 * o raio de ação e a contagem de comércios em diferentes distâncias.
 * Base de ruas e avenidas IBGE.
 *
 */
@Table(name = "localizacao_comercios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComercioRaioAvenida {

    /**
     * Identificador único do comércio.
     */
    private Long idComercio;
    /**
     * Nome do comércio.
     */
    private String nome;
    /**
     * A localização do comércio em formato Well-Known Text (WKT) "POINT(-47.3990964 -22.5692409)".
     */
    private String localizacaoTexto;
    /**
     * Raio de ação em metros (área de influência do comércio).
     * Define o alcance em metros ao redor do comércio para análises geoespaciais.
     */
    private Long raioAcaoMetros;
    /**
     * Contagem de vias e avenidas de comércios em um raio de 200 metros.
     */
    private int total200m;
    /**
     * Contagem de vias e avenidas de comércios em um raio de 500 metros.
     */
    private int total500m;
    /**
     * Contagem de vias e avenidas de comércios em um raio de 1 quilômetro.
     */
    private int total1km;
    /**
     * Contagem de vias e avenidas de comércios em um raio de 1,5 quilômetros.
     */
    private int total1_5km;
    /**
     * Contagem de vias e avenidas de comércios em um raio de 2 quilômetros.
     */
    private int total2km;
    /**
     * Contagem de vias e avenias no raio de ação definido.
     */
    private int totalRaioAcaoMetros;

}
