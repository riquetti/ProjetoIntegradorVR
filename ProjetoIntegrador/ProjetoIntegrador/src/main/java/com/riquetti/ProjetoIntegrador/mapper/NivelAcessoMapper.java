package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.NivelAcessoDTO;
import com.riquetti.ProjetoIntegrador.entity.NivelAcesso;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Interface de mapeamento para converter entre a entidade NivelAcesso e o DTO NivelAcessoDTO.
 */
@Mapper
public interface NivelAcessoMapper {

    /**
     * Instância do mapeador para uso em conversões.
     */
    NivelAcessoMapper INSTANCE = Mappers.getMapper(NivelAcessoMapper.class);

    /**
     * Converte uma entidade NivelAcesso para um DTO NivelAcessoDTO.
     *
     * @param entity A entidade a ser convertida.
     * @return O DTO correspondente à entidade fornecida.
     */
    NivelAcessoDTO toDTO(NivelAcesso entity);

    /**
     * Converte um DTO {@link NivelAcessoDTO} para uma entidade {@link NivelAcesso}.
     *
     * @param dto O DTO a ser convertido.
     * @return A entidade correspondente ao DTO fornecido.
     */
    NivelAcesso toEntity(NivelAcessoDTO dto);

}
