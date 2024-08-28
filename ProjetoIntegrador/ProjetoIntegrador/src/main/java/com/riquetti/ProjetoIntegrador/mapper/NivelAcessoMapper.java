package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.NivelAcessoDTO;
import com.riquetti.ProjetoIntegrador.entity.NivelAcesso;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NivelAcessoMapper {

    NivelAcessoMapper INSTANCE = Mappers.getMapper(NivelAcessoMapper.class);

    NivelAcessoDTO toDTO(NivelAcesso entity);
    NivelAcesso toEntity(NivelAcessoDTO dto);

}
