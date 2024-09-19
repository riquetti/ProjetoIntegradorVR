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
public class ComercioRaioVias {

    private Long idComercio;
    private String nome;
    private String descricao;
    private Long raioAcaoMetros;
    private BigDecimal comprimentoTotalPonderado;
    private String acessibilidade;
    private String localizacao;
    // Novos campos para os diferentes cálculos de raio
    private BigDecimal comprimento500;
    private BigDecimal comprimento1000;
    private BigDecimal comprimento1500;
    private BigDecimal comprimento2000;
    private BigDecimal comprimento2500;


}
