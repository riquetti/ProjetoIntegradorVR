package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private Integer idNivelAcesso;
    private Boolean ativo;
}
