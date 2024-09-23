package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.UsuarioDTO;
import com.riquetti.ProjetoIntegrador.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Interface de mapeamento para converter entre a entidade Usuario e o DTO UsuarioDTO.
 */
@Mapper
public interface UsuarioMapper {

    /**
     * Instância do mapeador para uso em conversões.
     */
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    /**
     * Converte uma entidade Usuario para um DTO UsuarioDTO.
     *
     * @param usuario A entidade a ser convertida.
     * @return O DTO correspondente à entidade fornecida.
     */
    UsuarioDTO toDto(Usuario usuario);

    /**
     * Converte um DTO UsuarioDTO} para uma entidade Usuario.
     *
     * @param usuarioDTO O DTO a ser convertido.
     * @return A entidade correspondente ao DTO fornecido.
     */
    Usuario toEntity(UsuarioDTO usuarioDTO);

}
