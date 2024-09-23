package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;


/**
 * Classe que representa um comércio e sua localização, incluindo informações sobre
 * o raio de ação e a contagem de comércios em diferentes distâncias.
 *
 * A classe é mapeada para a tabela "localizacao_comercios" no banco de dados.
 * Utiliza anotações do Lombok para reduzir a boilerplate code.
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
     * Representa a localização como WKT (Well-Known Text).
     */
    private String localizacaoTexto;
    /**
     * Raio de ação em metros (área de influência do comércio).
     */
    private Long raioAcaoMetros;
    /**
     * Contagem de comércios em um raio de 200 metros.
     */
    private int total200m;
    /**
     * Contagem de comércios em um raio de 500 metros.
     */
    private int total500m;
    /**
     * Contagem de comércios em um raio de 1 quilômetro.
     */
    private int total1km;
    /**
     * Contagem de comércios em um raio de 1,5 quilômetros.
     */
    private int total1_5km;
    /**
     * Contagem de comércios em um raio de 2 quilômetros.
     */
    private int total2km;
    /**
     * Contagem de comércios no raio de ação definido.
     */
    private int totalRaioAcaoMetros;

}
