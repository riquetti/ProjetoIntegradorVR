package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.NivelAcessoDTO;
import com.riquetti.ProjetoIntegrador.entity.NivelAcesso;
import com.riquetti.ProjetoIntegrador.mapper.NivelAcessoMapper;
import com.riquetti.ProjetoIntegrador.repository.NivelAcessoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Classe de serviço responsável por gerenciar as operações relacionadas ao NivelAcesso.
 * Esta classe atua como uma camada intermediária entre o controlador e o repositório,
 * fornecendo métodos para buscar, criar, atualizar e excluir registros de níveis de acesso.
 */
@Service
public class NivelAcessoService {

    private final NivelAcessoRepository nivelAcessoRepository;
    private final NivelAcessoMapper mapper = NivelAcessoMapper.INSTANCE;

    /**
     * Construtor para injeção de dependência do repositório.
     *
     * @param nivelAcessoRepository O repositório que gerencia as operações no banco de dados.
     */
    public NivelAcessoService(NivelAcessoRepository nivelAcessoRepository) {
        this.nivelAcessoRepository = nivelAcessoRepository;
    }

    /**
     * Recupera os detalhes de um nível de acesso específico com base em seu ID.
     *
     * @param idNivelAcesso O ID do nível de acesso a ser recuperado.
     * @return O DTO contendo as informações do nível de acesso.
     * @throws IllegalArgumentException Se o ID for nulo ou inválido.
     */
    public NivelAcessoDTO findById(Long idNivelAcesso) {
        validateIdNivelAcesso(idNivelAcesso);
        NivelAcesso nivelAcesso = nivelAcessoRepository.findById(idNivelAcesso);
        return mapper.toDTO(nivelAcesso);
    }

    /**
     * Recupera uma lista de todos os níveis de acesso cadastrados.
     *
     * @return Uma lista de DTOs representando todos os níveis de acesso.
     */
    public List<NivelAcessoDTO> findAll() {
        List<NivelAcesso> niveisAcesso = nivelAcessoRepository.findAll();
        return niveisAcesso.stream().map(mapper::toDTO).toList();
    }

    /**
     * Salva um novo registro de nível de acesso no banco de dados.
     *
     * @param nivelAcessoDTO O DTO contendo as informações do novo nível de acesso.
     * @throws IllegalArgumentException Se qualquer campo obrigatório do DTO for inválido.
     */
    public void save(NivelAcessoDTO nivelAcessoDTO) {
        validateNivelAcessoDTO(nivelAcessoDTO);
        NivelAcesso nivelAcesso = mapper.toEntity(nivelAcessoDTO);
        nivelAcessoRepository.save(nivelAcesso);
    }

    /**
     * Atualiza as informações de um nível de acesso existente.
     *
     * @param nivelAcessoDTO O DTO contendo as informações atualizadas do nível de acesso.
     * @throws IllegalArgumentException Se qualquer campo obrigatório do DTO for inválido.
     */
    public void update(NivelAcessoDTO nivelAcessoDTO) {
        validateNivelAcessoDTO(nivelAcessoDTO);
        NivelAcesso nivelAcesso = mapper.toEntity(nivelAcessoDTO);
        nivelAcessoRepository.update(nivelAcesso);
    }

    /**
     * Exclui um nível de acesso com base em seu ID.
     *
     * @param idNivelAcesso O ID do nível de acesso a ser excluído.
     * @throws IllegalArgumentException Se o ID for nulo ou inválido.
     */
    public void delete(Long idNivelAcesso) {
        validateIdNivelAcesso(idNivelAcesso);
        nivelAcessoRepository.delete(idNivelAcesso);
    }

    /**
     * Valida o ID do nível de acesso para garantir que ele não seja nulo e seja positivo.
     *
     * @param idNivelAcesso O ID do nível de acesso a ser validado.
     * @throws IllegalArgumentException Se o ID for nulo ou inválido.
     */
    private void validateIdNivelAcesso(Long idNivelAcesso) {
        if (idNivelAcesso == null || idNivelAcesso <= 0) {
            throw new IllegalArgumentException("O idNivelAcesso deve ser um número inteiro positivo e não pode ser nulo.");
        }
    }

    /**
     * Valida o DTO do nível de acesso para garantir que todos os campos obrigatórios sejam válidos.
     *
     * @param nivelAcessoDTO O DTO a ser validado.
     * @throws IllegalArgumentException Se qualquer campo obrigatório for inválido.
     */
    private void validateNivelAcessoDTO(NivelAcessoDTO nivelAcessoDTO) {
        if (nivelAcessoDTO.idNivelAcesso() == null || nivelAcessoDTO.idNivelAcesso() <= 0) {
            throw new IllegalArgumentException("O idNivelAcesso deve ser um número inteiro positivo e não pode ser nulo.");
        }
        if (nivelAcessoDTO.nivelAcesso() == null || nivelAcessoDTO.nivelAcesso().isEmpty()) {
            throw new IllegalArgumentException("O nível de acesso não pode ser nulo ou vazio.");
        }
        if (nivelAcessoDTO.nivelAcesso().length() > 20) {
            throw new IllegalArgumentException("O nível de acesso deve ter no máximo 20 caracteres.");
        }
    }
}

