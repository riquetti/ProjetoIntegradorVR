package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;


@Table(name = "localizacao_comercios")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LocalizacaoComercio {

    private long idComercio;
    private String nome;
    private String descricao;
    private long idTipoComercio;
    private BigDecimal raioAcaoMetros;
    private String localizacao;

}
