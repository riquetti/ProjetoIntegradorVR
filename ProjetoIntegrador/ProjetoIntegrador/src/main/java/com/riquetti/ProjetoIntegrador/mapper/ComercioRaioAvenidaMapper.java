package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioAvenidaDTO;
import com.riquetti.ProjetoIntegrador.entity.ComercioRaioAvenida;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Interface de mapeamento para converter entre a entidade ComercioRaioAvenida e o DTO ComercioRaioAvenidaDTO.
 */
@Mapper
public interface ComercioRaioAvenidaMapper {

    /**
     * Instância do mapeador para uso em conversões.
     */
    ComercioRaioAvenidaMapper INSTANCE = Mappers.getMapper(ComercioRaioAvenidaMapper.class);

    /**
     * Converte uma entidade ComercioRaioAvenida para um DTO ComercioRaioAvenidaDTO.
     *
     * @param entity A entidade a ser convertida.
     * @return O DTO correspondente à entidade fornecida.
     */
    ComercioRaioAvenidaDTO toDTO(ComercioRaioAvenida entity);

    /**
     * Converte um DTO ComercioRaioAvenidaDTO para uma entidade ComercioRaioAvenida.
     *
     * @param dto O DTO a ser convertido.
     * @return A entidade correspondente ao DTO fornecido.
     */
    ComercioRaioAvenida toEntity(ComercioRaioAvenidaDTO dto);
}
