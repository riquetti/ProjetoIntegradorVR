package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.ConcorrenteDTO;
import com.riquetti.ProjetoIntegrador.entity.Concorrente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConcorrenteMapper {

    ConcorrenteMapper INSTANCE = Mappers.getMapper(ConcorrenteMapper.class);

    ConcorrenteDTO toDTO(Concorrente entity);
    Concorrente toEntity(ConcorrenteDTO dto);

}
