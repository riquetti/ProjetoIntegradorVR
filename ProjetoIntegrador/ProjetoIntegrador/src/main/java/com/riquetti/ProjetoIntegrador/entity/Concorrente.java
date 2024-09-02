package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "localizacao_comercios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Concorrente {
    private Long idComercio;
    private String nome;
    private String descricao;
    private Long idTipoComercio;
    private String localizacao;
}
