package com.riquetti.ProjetoIntegrador.dto;

/**
 * Classe DTO que representa o nível de acesso no sistema.
 * @param idNivelAcesso O identificador único do nível de acesso.
 * @param nivelAcesso   O nome ou descrição do nível de acesso (por exemplo, "Administrador", "Usuário", etc.).
 */
public record NivelAcessoDTO(

        Long idNivelAcesso,
        String nivelAcesso

) {
}
