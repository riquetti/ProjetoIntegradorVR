package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/**
 * Representa a entidade que armazena informações sobre a localização de comércios.
 */
@Table(name = "localizacao_comercios")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LocalizacaoComercio {

    /**
     * ID do comércio Identificador único do comércio.
     */
    private long idComercio;

    /**
     * Nome do comércio.
     */
    private String nome;

    /**
     * Descrição opcional do comércio.
     */
    private String descricao;

    /**
     * ID do tipo de comércio, relacionando com a entidade TipoComercio.
     * Identifica o tipo do comercio armazenado.
     */
    private long idTipoComercio;

    /**
     * Raio de ação em metros (área de influência do comércio).
     *
     */
    private BigDecimal raioAcaoMetros;

    /**
     * Localização geográfica do comércio no formato WKT (Well-Known Text).
     * Exemplo: "POINT(-47.3990964 -22.5692409)".
     */
    private String localizacao;

}
