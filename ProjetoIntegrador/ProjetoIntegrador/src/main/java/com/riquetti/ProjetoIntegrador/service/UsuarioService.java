package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.UsuarioDTO;
import com.riquetti.ProjetoIntegrador.entity.Usuario;
import com.riquetti.ProjetoIntegrador.mapper.UsuarioMapper;
import com.riquetti.ProjetoIntegrador.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócios relacionada aos usuários do sistema.
 *
 * Esta classe gerencia as operações de Criar, Ler, Atualizar e Excluir
 * para os usuários, utilizando o repositório UsuarioRepository para
 * persistência de dados e o mapper UsuarioMapper para conversões
 * entre entidades e DTOs.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    /**
     * Construtor para inicializar o serviço de usuário.
     *
     * @param usuarioRepository O repositório de usuários.
     */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = UsuarioMapper.INSTANCE;
    }

    /**
     * Encontra um usuário pelo ID.
     *
     * @param id O ID do usuário a ser encontrado. Deve ser um número inteiro positivo.
     * @return O DTO do usuário correspondente ao ID.
     * @throws IllegalArgumentException Se o ID for nulo ou menor ou igual a 0.
     */
    public UsuarioDTO findById(Integer id) {
        validateId(id); // Validação do ID
        Usuario usuario = usuarioRepository.findById(id);
        return usuarioMapper.toDto(usuario);
    }

    /**
     * Retorna todos os usuários.
     *
     * @return Uma lista de DTOs de todos os usuários.
     */
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Salva um novo usuário.
     *
     * @param usuarioDTO O DTO do usuário a ser salvo.
     * @throws IllegalArgumentException Se o usuário não for válido.
     */
    public void save(UsuarioDTO usuarioDTO) {
        validateUsuarioDTO(usuarioDTO); // Validação do DTO
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuarioRepository.save(usuario);
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param usuarioDTO O DTO do usuário a ser atualizado.
     * @throws IllegalArgumentException Se o usuário não for válido.
     */
    public void update(UsuarioDTO usuarioDTO) {
        validateUsuarioDTO(usuarioDTO); // Validação do DTO
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuarioRepository.update(usuario);
    }

    /**
     * Exclui um usuário pelo ID.
     *
     * @param id O ID do usuário a ser excluído.
     * @throws IllegalArgumentException Se o ID for nulo ou menor ou igual a 0.
     */
    public void delete(Integer id) {
        validateId(id); // Validação do ID
        usuarioRepository.delete(id);
    }

    // Métodos de validação

    /**
     * Valida o ID fornecido.
     *
     * @param id O ID a ser validado.
     * @throws IllegalArgumentException Se o ID for nulo ou menor ou igual a 0.
     */
    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID deve ser um número inteiro positivo e não pode ser nulo.");
        }
    }

    /**
     * Valida o DTO do usuário.
     *
     * @param usuarioDTO O DTO do usuário a ser validado.
     * @throws IllegalArgumentException Se o DTO não for válido.
     */
    private void validateUsuarioDTO(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.nome() == null || usuarioDTO.nome().isEmpty() || usuarioDTO.nome().length() > 100) {
            throw new IllegalArgumentException("O nome não pode ser nulo, vazio ou ultrapassar 100 caracteres.");
        }

        if (usuarioDTO.email() == null || usuarioDTO.email().isEmpty() || !isValidEmail(usuarioDTO.email())) {
            throw new IllegalArgumentException("O email não pode ser nulo, vazio e deve ser um email válido.");
        }

        if (usuarioDTO.senha() == null || usuarioDTO.senha().isEmpty() || usuarioDTO.senha().length() > 255) {
            throw new IllegalArgumentException("A senha não pode ser nula, vazia ou ultrapassar 255 caracteres.");
        }

        if (usuarioDTO.idNivelAcesso() == null || usuarioDTO.idNivelAcesso() <= 0) {
            throw new IllegalArgumentException("O idNivelAcesso não pode ser nulo e deve ser um número inteiro positivo.");
        }
    }

    /**
     * Valida o formato do email.
     *
     * @param email O email a ser validado.
     * @return {@code true} se o email for válido; {@code false} caso contrário.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,}$"; // Regex simples para validação de email
        return email.matches(emailRegex);
    }
}