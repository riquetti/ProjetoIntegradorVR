package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/**
 * Entidade que representa os dados de comércio relacionados ao IBGE
 * por comércio dentro do raio de ação (dados IBGE).
 */
@Table(name = "localizacao_comercios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComercioDadosIbge {

    /**
     * ID do comércio.
     * Representa o identificador único de um comércio.
     */
    private Long idComercio;

    /**
     * Nome do comércio. Identificação
     */
    private String nome;

    /**
     * Localização em formato texto (WKT).
     * Armazena a representação da localização do comércio no formato Well-Known Text (WKT).
     * "POINT(-47.3990964 -22.5692409)".
     */
    private String localizacaoTexto;

    /**
     * Raio de ação em metros (área de influência do comércio).
     * Define o alcance em metros ao redor do comércio para análises geoespaciais.
     */
    private Long raioAcaoMetros;

    /**
     * Renda média por setor censitário, de acordo com o IBGE em 2010.
     * Representa a renda média dos residentes dentro do raio de ação ao comércio.
     */
    private BigDecimal rendaMediaIbge2010;

    /**
     * Número de moradores por setor censitário, de acordo com o IBGE em 2010.
     * Soma do número de moradores dentro do raio de ação do comércio.
     */
    private BigDecimal moradoresIbge2010;
}
