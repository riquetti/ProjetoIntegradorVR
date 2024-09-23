package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioViasDTO;
import com.riquetti.ProjetoIntegrador.entity.ComercioRaioVias;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *  Interface de mapeamento para converter entre a entidade ComercioRaioVias e o DTO ComercioRaioViasDTO.
 */
@Mapper(componentModel = "spring")
public interface ComercioRaioViasMapper {

    /**
     * Instância do mapeador para uso em conversões.
     */
    ComercioRaioViasMapper INSTANCE = Mappers.getMapper(ComercioRaioViasMapper.class);

    /**
     * Converte uma entidade ComercioRaioVias para um DTO ComercioRaioViasDTO.
     *
     * @param entity A entidade a ser convertida.
     * @return O DTO correspondente à entidade fornecida.
     */
    ComercioRaioViasDTO toDTO(ComercioRaioVias entity);

    /**
     * Converte um DTO ComercioRaioViasDTO para uma entidade ComercioRaioVias.
     *
     * @param dto O DTO a ser convertido.
     * @return A entidade correspondente ao DTO fornecido.
     */
    ComercioRaioVias toEntity(ComercioRaioViasDTO dto);

}
