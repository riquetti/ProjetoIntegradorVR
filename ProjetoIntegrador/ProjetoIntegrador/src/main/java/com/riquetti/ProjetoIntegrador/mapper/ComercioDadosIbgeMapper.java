package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.ComercioDadosIbgeDTO;
import com.riquetti.ProjetoIntegrador.entity.ComercioDadosIbge;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Interface Mapper responsável por converter entre as entidades ComercioDadosIbge e ComercioDadosIbgeDTO.
 */
@Mapper
public interface ComercioDadosIbgeMapper {

    /**
     * Instância do mapeador para uso em conversões.
     */
    ComercioDadosIbgeMapper INSTANCE = Mappers.getMapper(ComercioDadosIbgeMapper.class);

    /**
     * Converte uma entidade `ComercioDadosIbge` para um DTO `ComercioDadosIbgeDTO`.
     *
     * @param entity a entidade `ComercioDadosIbge` a ser convertida.
     * @return o DTO correspondente com os dados da entidade.
     */
    ComercioDadosIbgeDTO toDTO(ComercioDadosIbge entity);

    /**
     * Converte um DTO `ComercioDadosIbgeDTO` para uma entidade `ComercioDadosIbge`.
     *
     * @param dto o DTO `ComercioDadosIbgeDTO` a ser convertido.
     * @return a entidade correspondente com os dados do DTO.
     */
    ComercioDadosIbge toEntity(ComercioDadosIbgeDTO dto);
}