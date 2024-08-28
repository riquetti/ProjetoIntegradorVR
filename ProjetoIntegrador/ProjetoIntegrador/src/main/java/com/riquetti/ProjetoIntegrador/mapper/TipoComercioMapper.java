package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.TipoComercioDTO;
import com.riquetti.ProjetoIntegrador.entity.TipoComercio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TipoComercioMapper {

    TipoComercioMapper INSTANCE = Mappers.getMapper(TipoComercioMapper.class);

    TipoComercioDTO toDTO(TipoComercio entity);
    TipoComercio toEntity(TipoComercioDTO dto);

}
