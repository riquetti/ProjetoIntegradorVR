package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioAvenidaDTO;
import com.riquetti.ProjetoIntegrador.entity.ComercioRaioAvenida;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ComercioRaioAvenidaMapper {

    ComercioRaioAvenidaMapper INSTANCE = Mappers.getMapper(ComercioRaioAvenidaMapper.class);

    ComercioRaioAvenidaDTO toDTO(ComercioRaioAvenida entity);
    ComercioRaioAvenida toEntity(ComercioRaioAvenidaDTO dto);
}
