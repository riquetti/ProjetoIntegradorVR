package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/**
 * Entidade que representa os dados de comércio relacionados ao IBGE.
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
     * Nome do comércio.
     * Nome atribuído ao comércio para identificação.
     */
    private String nome;

    /**
     * Localização em formato texto (WKT).
     * Armazena a representação da localização do comércio no formato Well-Known Text (WKT).
     * "POINT(-47.3990964 -22.5692409)".
     */
    private String localizacaoTexto; // Para armazenar o resultado da função ST_AsText

    /**
     * Raio de ação em metros.
     * Define o alcance em metros ao redor do comércio para análises espaciais.
     */
    private Long raioAcaoMetros;

    /**
     * Renda média de acordo com o IBGE em 2010.
     * Representa a renda média dos residentes próximos ao comércio, de acordo com dados de 2010 do IBGE.
     */
    private BigDecimal rendaMediaIbge2010;

    /**
     * Número de moradores de acordo com o IBGE em 2010.
     * Número de moradores nas áreas próximas ao comércio, de acordo com o censo do IBGE de 2010.
     */
    private BigDecimal moradoresIbge2010;
}
