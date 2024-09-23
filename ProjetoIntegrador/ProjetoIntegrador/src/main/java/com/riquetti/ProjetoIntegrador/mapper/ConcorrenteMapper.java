package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.ConcorrenteDTO;
import com.riquetti.ProjetoIntegrador.entity.Concorrente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Interface de mapeamento para converter entre a entidade Concorrente e o DTO ConcorrenteDTO.
 */
@Mapper
public interface ConcorrenteMapper {

    /**
     * Instância do mapeador para uso em conversões.
     */
    ConcorrenteMapper INSTANCE = Mappers.getMapper(ConcorrenteMapper.class);

    /**
     * Converte uma entidade Concorrente para um DTO ConcorrenteDTO.
     *
     * @param entity A entidade a ser convertida.
     * @return O DTO correspondente à entidade fornecida.
     */
    ConcorrenteDTO toDTO(Concorrente entity);

    /**
     * Converte um DTO ConcorrenteDTO para uma entidade Concorrente.
     *
     * @param dto O DTO a ser convertido.
     * @return A entidade correspondente ao DTO fornecido.
     */
    Concorrente toEntity(ConcorrenteDTO dto);

}
