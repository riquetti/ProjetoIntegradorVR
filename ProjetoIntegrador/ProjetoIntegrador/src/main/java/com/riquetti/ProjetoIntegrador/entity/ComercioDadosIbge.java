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

public class ComercioDadosIbge {
    private Long idComercio;
    private String nome;
    private String localizacaoTexto; // Para armazenar o resultado da função ST_AsText
    private Long raioAcaoMetros;
    private BigDecimal rendaMediaIbge2010;
}
