package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Representa a entidade que armazena informações sobre os tipos de comércio.
 * Esta classe é utilizada para categorizar diferentes tipos de estabelecimentos comerciais no sistema.
 */
@Table(name = "tipo_comercio")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TipoComercio {

    /**
     * ID único do tipo de comércio.
     */
    private long idTipoComercio;

    /**
     * Descrição do tipo de comércio. Exemplo: "Supermercado", "Restaurante", etc.
     */
    private String descricao;

}
