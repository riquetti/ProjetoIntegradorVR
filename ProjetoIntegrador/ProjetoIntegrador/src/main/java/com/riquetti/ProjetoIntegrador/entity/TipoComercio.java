package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;


@Table(name = "tipo_comercio")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TipoComercio {
    private long idTipoComercio;
    private String descricao;
}
