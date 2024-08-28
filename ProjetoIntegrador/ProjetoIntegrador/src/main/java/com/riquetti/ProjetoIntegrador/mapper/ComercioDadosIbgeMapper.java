package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.ComercioDadosIbgeDTO;
import com.riquetti.ProjetoIntegrador.entity.ComercioDadosIbge;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ComercioDadosIbgeMapper {
    ComercioDadosIbgeMapper INSTANCE = Mappers.getMapper(ComercioDadosIbgeMapper.class);

    ComercioDadosIbgeDTO toDTO(ComercioDadosIbge entity);
}