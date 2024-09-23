package com.riquetti.ProjetoIntegrador.mapper;

import com.riquetti.ProjetoIntegrador.dto.LocalizacaoComercioDTO;
import com.riquetti.ProjetoIntegrador.entity.LocalizacaoComercio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocalizacaoComercioMapper {

    /**
     * Interface de mapeamento para converter entre a entidade LocalizacaoComercio e o DTO LocalizacaoComercioDTO.
     */
    LocalizacaoComercioMapper INSTANCE = Mappers.getMapper(LocalizacaoComercioMapper.class);

    /**
     * Converte uma entidade LocalizacaoComercio para um DTO LocalizacaoComercioDTO.
     *
     * @param entity A entidade a ser convertida.
     * @return O DTO correspondente à entidade fornecida.
     */
    LocalizacaoComercioDTO toDTO(LocalizacaoComercio entity);

    /**
     * Converte um DTO LocalizacaoComercioDTO para uma entidade LocalizacaoComercio.
     *
     * @param dto O DTO a ser convertido.
     * @return A entidade correspondente ao DTO fornecido.
     */
    LocalizacaoComercio toEntity(LocalizacaoComercioDTO dto);

}
