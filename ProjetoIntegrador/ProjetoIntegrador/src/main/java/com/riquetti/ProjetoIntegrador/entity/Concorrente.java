package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entidade que representa um concorrente em um sistema de análise de localização de comércios.
 */
@Table(name = "localizacao_comercios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Concorrente {
    /**
     * Identificador único do comércio.
     */
    private Long idComercio;

    /**
     * Nome do comércio.
     */
    private String nome;

    /**
     * Descrição detalhada do comércio.
     */
    private String descricao;

    /**
     * Identificador do tipo de comércio.
     */
    private Long idTipoComercio;

    /**
     * Localização geográfica do comércio concorrente em formato WKT (Well-Known Text).
     */
    private String localizacao;
}
