package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.TipoComercioDTO;
import com.riquetti.ProjetoIntegrador.entity.TipoComercio;
import com.riquetti.ProjetoIntegrador.mapper.TipoComercioMapper;
import com.riquetti.ProjetoIntegrador.repository.TipoComercioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe de serviço responsável por gerenciar as operações relacionadas ao TipoComercio.
 * Esta classe atua como uma camada intermediária entre o controlador e o repositório,
 * fornecendo métodos para buscar, criar, atualizar e excluir registros de tipos de comércio.
 */
@Service
public class TipoComercioService {

    private final TipoComercioRepository tipoComercioRepository;
    private final TipoComercioMapper tipoComercioMapper;

    /**
     * Construtor para injeção de dependência do repositório.
     *
     * @param tipoComercioRepository O repositório que gerencia as operações no banco de dados.
     */
    public TipoComercioService(TipoComercioRepository tipoComercioRepository) {
        this.tipoComercioRepository = tipoComercioRepository;
        this.tipoComercioMapper = TipoComercioMapper.INSTANCE;
    }

    /**
     * Recupera os detalhes de um tipo de comércio específico com base em seu ID.
     *
     * @param idTipoComercio O ID do tipo de comércio a ser recuperado.
     * @return O DTO contendo as informações do tipo de comércio.
     * @throws IllegalArgumentException Se o ID for nulo ou inválido.
     */
    public TipoComercioDTO findById(Long idTipoComercio) {
        validateIdTipoComercio(idTipoComercio);
        TipoComercio tipoComercio = tipoComercioRepository.findById(idTipoComercio);
        return tipoComercioMapper.toDTO(tipoComercio);
    }

    /**
     * Recupera uma lista de todos os tipos de comércio cadastrados.
     *
     * @return Uma lista de DTOs representando todos os tipos de comércio.
     */
    public List<TipoComercioDTO> findAll() {
        return tipoComercioRepository.findAll().stream()
                .map(tipoComercioMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Salva um novo registro de tipo de comércio no banco de dados.
     *
     * @param tipoComercioDTO O DTO contendo as informações do novo tipo de comércio.
     * @throws IllegalArgumentException Se qualquer campo obrigatório do DTO for inválido.
     */
    public void save(TipoComercioDTO tipoComercioDTO) {
        validateTipoComercioDTO(tipoComercioDTO);
        TipoComercio tipoComercio = tipoComercioMapper.toEntity(tipoComercioDTO);
        tipoComercioRepository.save(tipoComercio);
    }

    /**
     * Atualiza as informações de um tipo de comércio existente.
     *
     * @param tipoComercioDTO O DTO contendo as informações atualizadas do tipo de comércio.
     * @throws IllegalArgumentException Se qualquer campo obrigatório do DTO for inválido.
     */
    public void update(TipoComercioDTO tipoComercioDTO) {
        validateTipoComercioDTO(tipoComercioDTO);
        TipoComercio tipoComercio = tipoComercioMapper.toEntity(tipoComercioDTO);
        tipoComercioRepository.update(tipoComercio);
    }

    /**
     * Exclui um tipo de comércio com base em seu ID.
     *
     * @param idTipoComercio O ID do tipo de comércio a ser excluído.
     * @throws IllegalArgumentException Se o ID for nulo ou inválido.
     */
    public void delete(Long idTipoComercio) {
        validateIdTipoComercio(idTipoComercio);
        tipoComercioRepository.delete(idTipoComercio);
    }

    /**
     * Valida o ID do tipo de comércio para garantir que ele não seja nulo e seja positivo.
     *
     * @param idTipoComercio O ID do tipo de comércio a ser validado.
     * @throws IllegalArgumentException Se o ID for nulo ou inválido.
     */
    private void validateIdTipoComercio(Long idTipoComercio) {
        if (idTipoComercio == null || idTipoComercio <= 0) {
            throw new IllegalArgumentException("O idTipoComercio deve ser um número inteiro positivo e não pode ser nulo.");
        }
    }

    /**
     * Valida o DTO do tipo de comércio para garantir que todos os campos obrigatórios sejam válidos.
     *
     * @param tipoComercioDTO O DTO a ser validado.
     * @throws IllegalArgumentException Se qualquer campo obrigatório for inválido.
     */
    private void validateTipoComercioDTO(TipoComercioDTO tipoComercioDTO) {
        if (tipoComercioDTO.descricao() == null || tipoComercioDTO.descricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição do tipo de comércio não pode ser nula ou vazia.");
        }
        if (tipoComercioDTO.descricao().length() > 255) {
            throw new IllegalArgumentException("A descrição do tipo de comércio deve ter no máximo 255 caracteres.");
        }

        if (tipoComercioDTO.idTipoComercio() == null || tipoComercioDTO.idTipoComercio() <= 0) {
            throw new IllegalArgumentException("O idTipoComercio deve ser um número inteiro positivo e não pode ser nulo.");
        }
    }

}

