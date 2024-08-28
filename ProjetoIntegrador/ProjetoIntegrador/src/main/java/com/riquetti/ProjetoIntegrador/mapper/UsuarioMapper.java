package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.UsuarioDTO;
import com.riquetti.ProjetoIntegrador.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioDTO toDto(Usuario usuario);
    Usuario toEntity(UsuarioDTO usuarioDTO);

}
