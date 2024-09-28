package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.LocalizacaoComercioDTO;
import com.riquetti.ProjetoIntegrador.entity.LocalizacaoComercio;
import com.riquetti.ProjetoIntegrador.mapper.LocalizacaoComercioMapper;
import com.riquetti.ProjetoIntegrador.repository.LocalizacaoComercioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe de serviço responsável por gerenciar as operações relacionadas a LocalizacaoComercio.
 * Esta classe atua como uma camada intermediária entre o controlador e o repositório,
 * fornecendo métodos para buscar, criar, atualizar e excluir registros de comércio.
 */
@Service
public class LocalizacaoComercioService {

    private final LocalizacaoComercioRepository repository;
    private final LocalizacaoComercioMapper mapper = LocalizacaoComercioMapper.INSTANCE;

    /**
     * Construtor para injeção de dependência do repositório.
     *
     * @param repository O repositório que gerencia as operações no banco de dados.
     */
    public LocalizacaoComercioService(LocalizacaoComercioRepository repository) {
        this.repository = repository;
    }

    /**
     * Recupera os detalhes de um comércio específico com base em seu ID.
     *
     * @param idComercio O ID do comércio a ser recuperado.
     * @return O DTO contendo as informações do comércio.
     * @throws IllegalArgumentException Se o ID for nulo ou inválido.
     */
    public LocalizacaoComercioDTO getLocalizacaoComercioById(Long idComercio) {
        validateIdComercio(idComercio);
        LocalizacaoComercio comercio = repository.findById(idComercio);
        return mapper.toDTO(comercio);
    }

    /**
     * Recuperar uma lista de todos os comércios cadastrados.
     *
     * @return Uma lista de DTOs representando todos os comércios.
     */
    public List<LocalizacaoComercioDTO> getAllLocalizacaoComercios() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Criar um novo registro de comércio no banco de dados.
     *
     * @param dto O DTO contendo as informações do novo comércio.
     * @throws IllegalArgumentException Se qualquer campo obrigatório do DTO for inválido.
     */
    public void createLocalizacaoComercio(LocalizacaoComercioDTO dto) {
        validateLocalizacaoComercioDTO(dto);
        LocalizacaoComercio comercio = mapper.toEntity(dto);
        repository.save(comercio);
    }

    /**
     * Atualizar as informações de um comércio existente.
     *
     * @param dto O DTO contendo as informações atualizadas do comércio.
     * @throws IllegalArgumentException Se qualquer campo obrigatório do DTO for inválido.
     */
    public void updateLocalizacaoComercio(LocalizacaoComercioDTO dto) {
        validateLocalizacaoComercioDTO(dto);
        LocalizacaoComercio comercio = mapper.toEntity(dto);
        repository.update(comercio);
    }

    /**
     * Excluir um comércio com base em seu ID.
     *
     * @param idComercio O ID do comércio a ser excluído.
     * @throws IllegalArgumentException Se o ID for nulo ou inválido.
     */
    public void deleteLocalizacaoComercio(Long idComercio) {
        validateIdComercio(idComercio);
        repository.delete(idComercio);
    }

    /**
     * Validar o ID do comércio para garantir que ele não seja nulo e seja positivo.
     *
     * @param idComercio O ID do comércio a ser validado.
     * @throws IllegalArgumentException Se o ID for nulo ou inválido.
     */
    private void validateIdComercio(Long idComercio) {
        if (idComercio == null || idComercio <= 0) {
            throw new IllegalArgumentException("O idComercio deve ser um número inteiro positivo e não pode ser nulo.");
        }
    }

    /**
     * Validar o DTO do comércio para garantir que todos os campos obrigatórios sejam válidos.
     *
     * @param dto O DTO a ser validado.
     * @throws IllegalArgumentException Se qualquer campo obrigatório for inválido.
     */
    private void validateLocalizacaoComercioDTO(LocalizacaoComercioDTO dto) {
        if (dto.nome() == null || dto.nome().isEmpty()) {
            throw new IllegalArgumentException("O nome do comércio não pode ser nulo ou vazio.");
        }
        if (dto.descricao() == null || dto.descricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição do comércio não pode ser nula ou vazia.");
        }
        if (dto.raioAcaoMetros() == null || dto.raioAcaoMetros().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O raio de ação deve ser um valor positivo.");
        }
        if (dto.idTipoComercio() == null || dto.idTipoComercio() <= 0) {
            throw new IllegalArgumentException("O tipo de comércio deve ser um número inteiro positivo.");
        }
        if (dto.localizacao() == null || !isValidPointFormat(dto.localizacao())) {
            throw new IllegalArgumentException("A localização deve estar no formato 'POINT(lon lat)' e não pode ser nula.");
        }
    }

    /**
     * Validar a string de localização está no formato 'POINT(lon lat)'.
     *
     * @param localizacao A string de localização a ser validada.
     * @return True se a string estiver no formato correto, caso contrário, False.
     */
    private boolean isValidPointFormat(String localizacao) {
        String regex = "^POINT\\(-?\\d+\\.\\d+ -?\\d+\\.\\d+\\)$";
        return localizacao.matches(regex);
    }
}

