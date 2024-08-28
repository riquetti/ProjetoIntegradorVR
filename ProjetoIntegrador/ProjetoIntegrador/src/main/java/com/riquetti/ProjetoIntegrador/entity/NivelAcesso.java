package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "nivel_acesso")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NivelAcesso {

    private Long idNivelAcesso;
    private String nivelAcesso;

}
