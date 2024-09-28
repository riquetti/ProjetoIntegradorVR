package com.riquetti.ProjetoIntegrador.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Representa a entidade que armazena informações sobre os usuários.
 * Esta classe é utilizada para armazenar as informações do usuário para efetuar o login na aplicação.
 */

@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    /**
     * ID único do usuário, utilizado para identificá-lo no sistema.
     */
    private Integer id;

    /**
     * Nome completo do usuário.
     */
    private String nome;

    /**
     * Endereço de email do usuário.
     */
    private String email;

    /**
     * Senha do usuário, utilizada para autenticação.
     */
    private String senha;

    /**
     * ID do nível de acesso associado ao usuário, que determina suas permissões no sistema.
     */
    private Integer idNivelAcesso;

    /**
     * Indica se o usuário está ativo ou inativo no sistema.
     */
    private Boolean ativo;
}
