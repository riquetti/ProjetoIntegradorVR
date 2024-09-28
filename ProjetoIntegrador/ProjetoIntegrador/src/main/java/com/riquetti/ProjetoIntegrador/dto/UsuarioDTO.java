package com.riquetti.ProjetoIntegrador.dto;

/**
 * Classe DTO que representa um usuário no sistema.
 * @param id                O identificador único do usuário.
 * @param nome              O nome completo do usuário.
 * @param email             O endereço de e-mail do usuário, utilizado para login.
 * @param senha             A senha do usuário, utilizada para autenticação. Desenvolver tratativa de segurança.
 * @param idNivelAcesso     O identificador do nível de acesso do usuário, que determina suas permissões no sistema.
 * @param ativo             Indica se o usuário está ativo no sistema. Pode ser usado para controlar o status de login.
 */
public record UsuarioDTO(

        Integer id,
        String nome,
        String email,
        String senha,
        Integer idNivelAcesso,
        Boolean ativo

) {
}
