package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.LocalizacaoComercioDTO;
import com.riquetti.ProjetoIntegrador.entity.LocalizacaoComercio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocalizacaoComercioMapper {

    LocalizacaoComercioMapper INSTANCE = Mappers.getMapper(LocalizacaoComercioMapper.class);

    LocalizacaoComercioDTO toDTO(LocalizacaoComercio entity);
    LocalizacaoComercio toEntity(LocalizacaoComercioDTO dto);

}
