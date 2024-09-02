package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "localizacao_comercios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComercioRaioAvenida {

    private Long idComercio;
    private String nome;
    private String localizacaoTexto; // Representa a localização como WKT (Well-Known Text)
    private Long raioAcaoMetros;
    private int total200m;
    private int total500m;
    private int total1km;
    private int total1_5km;
    private int total2km;
    private int totalRaioAcaoMetros;

}
