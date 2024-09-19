package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioViasDTO;
import com.riquetti.ProjetoIntegrador.entity.ComercioRaioVias;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ComercioRaioViasMapper {

    ComercioRaioViasMapper INSTANCE = Mappers.getMapper(ComercioRaioViasMapper.class);

    ComercioRaioViasDTO toDTO(ComercioRaioVias entity);
    ComercioRaioVias toEntity(ComercioRaioViasDTO dto);

}
