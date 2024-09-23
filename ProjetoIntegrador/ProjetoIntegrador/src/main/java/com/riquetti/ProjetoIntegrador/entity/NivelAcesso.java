package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Representa a entidade que armazena informações sobre o nível de acesso dos usuários.
 * Esta classe é mapeada para a tabela "nivel_acesso" no banco de dados.
 */

@Table(name = "nivel_acesso")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NivelAcesso {

    /**
     * ID único do nível de acesso, gerado automaticamente pelo banco de dados.
     */
    private Long idNivelAcesso;

    /**
     * Descrição do nível de acesso. Exemplo: "Administrador", "Usuário", etc.
     */
    private String nivelAcesso;

}
