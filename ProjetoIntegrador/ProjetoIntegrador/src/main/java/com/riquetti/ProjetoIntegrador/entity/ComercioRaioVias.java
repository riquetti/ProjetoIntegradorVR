package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/**
 *  Entidade que representa as localizações dos comércios,
 *  relacionadas ao raio de ação e à acessibilidade de vias ao redor do comércio.
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
     * Raio de ação do comércio em metros (área de influência do comércio).
     */
    private Long raioAcaoMetros;

    /**
     * Comprimento total das vias ao redor do comércio ponderado.
     */
    private BigDecimal comprimentoTotalPonderado;

    /**
     * Descrição da acessibilidade das vias ao redor do comércio.
     */
    private String acessibilidade;

    /**
     * Localização geográfica do comércio em formato WKT (Well-Known Text).
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
