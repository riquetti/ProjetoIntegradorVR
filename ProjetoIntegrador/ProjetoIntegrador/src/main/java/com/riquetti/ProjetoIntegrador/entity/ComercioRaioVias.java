package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/**
 *  Entidade que representa as localizações dos comércios,
 *  relacionadas ao raio de ação e à acessibilidade de vias ao redor do comércio.
 * Base OSM (open street map)
 *
 */
@Table(name = "localizacao_comercios")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ComercioRaioVias {

    /**
     * Identificador único do comércio.
     */
    private Long idComercio;

    /**
     * Nome do comércio.
     */
    private String nome;

    /**
     * Descrição do comércio.
     */
    private String descricao;

    /**
     * Raio de ação em metros (área de influência do comércio).
     * Define o alcance em metros ao redor do comércio para análises geoespaciais.
     */
    private Long raioAcaoMetros;

    /**
     * Comprimento total das vias ao redor do comércio ponderado.
     * Cálculo do comprimento das vias dentro do raio de ação.
     * Atribui peso para diferentes tipos de vias.
     */
    private BigDecimal comprimentoTotalPonderado;

    /**
     * Descrição da acessibilidade das vias ao redor do comércio.
     * Classificação da acessibilidade em muito ruim, ruim, média, boa e Excelente
     */
    private String acessibilidade;

    /**
     * Localização geográfica do comércio em formato WKT (Well-Known Text).
     * "POINT(-47.3990964 -22.5692409)".
     */
    private String localizacao;

    /**
     * Comprimento das vias dentro de um raio de 500 metros do comércio.
     */
    private BigDecimal comprimento500;

    /**
     * Comprimento das vias dentro de um raio de 1000 metros do comércio.
     */
    private BigDecimal comprimento1000;

    /**
     * Comprimento das vias dentro de um raio de 1500 metros do comércio.
     */
    private BigDecimal comprimento1500;

    /**
     * Comprimento das vias dentro de um raio de 2000 metros do comércio.
     */
    private BigDecimal comprimento2000;

    /**
     * Comprimento das vias dentro de um raio de 2500 metros do comércio.
     */
    private BigDecimal comprimento2500;

}
