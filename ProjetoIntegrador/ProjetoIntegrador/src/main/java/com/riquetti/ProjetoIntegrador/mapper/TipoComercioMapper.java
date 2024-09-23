package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.TipoComercioDTO;
import com.riquetti.ProjetoIntegrador.entity.TipoComercio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Interface de mapeamento para converter entre a entidade TipoComercio e o DTO TipoComercioDTO.
 */
@Mapper
public interface TipoComercioMapper {

    /**
     * Instância do mapeador para uso em conversões.
     */
    TipoComercioMapper INSTANCE = Mappers.getMapper(TipoComercioMapper.class);

    /**
     * Converte uma entidade TipoComercio para um DTO TipoComercioDTO.
     *
     * @param entity A entidade a ser convertida.
     * @return O DTO correspondente à entidade fornecida.
     */
    TipoComercioDTO toDTO(TipoComercio entity);

    /**
     * Converte um DTO TipoComercioDTO} para uma entidade TipoComercio.
     *
     * @param dto O DTO a ser convertido.
     * @return A entidade correspondente ao DTO fornecido.
     */
    TipoComercio toEntity(TipoComercioDTO dto);

}
