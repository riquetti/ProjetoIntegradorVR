package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioAvenidaDTO;
import com.riquetti.ProjetoIntegrador.entity.ComercioRaioAvenida;
import com.riquetti.ProjetoIntegrador.exception.InvalidInputException;
import com.riquetti.ProjetoIntegrador.mapper.ComercioRaioAvenidaMapper;
import com.riquetti.ProjetoIntegrador.repository.ComercioRaioAvenidaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócios relacionada a comércios e raios de ação.
 */
@Service
public class ComercioRaioAvenidaService {

    private final ComercioRaioAvenidaRepository repository;
    private final ComercioRaioAvenidaMapper mapper = ComercioRaioAvenidaMapper.INSTANCE;

    /**
     * Construtor da classe ComercioRaioAvenidaService.
     *
     * @param repository Repositório para acessar dados de comércios.
     */
    public ComercioRaioAvenidaService(ComercioRaioAvenidaRepository repository) {
        this.repository = repository;
    }

    /**
     * Recupera todos os comércios.
     *
     * @return Lista de DTOs de comércios.
     */
    public List<ComercioRaioAvenidaDTO> findAll() {
        List<ComercioRaioAvenida> entities = repository.findAllComercios();
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera comércios pelo ID.
     *
     * @param idComercio ID do comércio a ser buscado.
     * @return Lista de DTOs de comércios correspondentes ao ID.
     * @throws IllegalArgumentException se o ID do comércio for nulo ou menor ou igual a 0.
     */
    public List<ComercioRaioAvenidaDTO> getComerciosById(Long idComercio) {
        validateIdComercio(idComercio);
        List<ComercioRaioAvenida> entities = repository.findComerciosById(idComercio);
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera um comércio pelo ID e raio de ação.
     *
     * @param idComercio      ID do comércio a ser buscado.
     * @param raioAcaoMetros Raio de ação em metros.
     * @return DTO do comércio correspondente.
     * @throws IllegalArgumentException se o ID do comércio ou raio de ação forem nulos ou inválidos.
     */
    public ComercioRaioAvenidaDTO getComercioByIdAndRaio(Long idComercio, Double raioAcaoMetros) {
        // Validação
        if (idComercio == null || idComercio <= 0) {
            throw new IllegalArgumentException("idComercio deve ser um número maior que 0.");
        }
        if (raioAcaoMetros == null || raioAcaoMetros <= 0) {
            throw new IllegalArgumentException("raioAcaoMetros não pode ser nulo e deve ser maior que 0.");
        }

        ComercioRaioAvenida entity = repository.findComercioByIdAndRaio(idComercio, raioAcaoMetros);
        return mapper.toDTO(entity);
    }

    /**
     * Recupera comércios baseados em uma localização textual.
     *
     * @param localizacaoTexto Texto representando a localização em formato POINT.
     * @return Lista de DTOs de comércios correspondentes à localização.
     * @throws InvalidInputException se a localização não estiver no formato esperado.
     */
    public List<ComercioRaioAvenidaDTO> getPontoRaioAvenida(String localizacaoTexto) {
        validateLocalizacaoTexto(localizacaoTexto);
        List<ComercioRaioAvenida> entities = repository.findPontoRaioAvenida(localizacaoTexto);
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera comércios dentro de um raio a partir de uma localização.
     *
     * @param localizacaoTexto Texto representando a localização em formato POINT.
     * @param raioAcaopersolizado Raio de ação em metros.
     * @return Lista de DTOs de comércios correspondentes à localização e raio.
     * @throws InvalidInputException se a localização ou raio forem inválidos.
     */
    public List<ComercioRaioAvenidaDTO> getLocalizacaoRaioAvenida(String localizacaoTexto, double raioAcaopersolizado) {
        validateLocalizacaoTexto(localizacaoTexto);
        validateRaioAcaoMetros(raioAcaopersolizado);
        List<ComercioRaioAvenida> entities = repository.findLocalizacaoRaioAvenida(localizacaoTexto, raioAcaopersolizado);
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Validações
    private void validateIdComercio(Long idComercio) {
        if (idComercio == null || idComercio <= 0) {
            throw new InvalidInputException("idComercio deve ser um número maior que 0.");
        }
    }

    private void validateRaioAcaoMetros(Double raioAcaoMetros) {
        if (raioAcaoMetros == null || raioAcaoMetros <= 0) {
            throw new InvalidInputException("raioAcaoMetros não pode ser nulo e deve ser maior que 0.");
        }
    }

    private void validateLocalizacaoTexto(String localizacaoTexto) {
        if (localizacaoTexto == null || !localizacaoTexto.startsWith("POINT(") || !localizacaoTexto.endsWith(")")) {
            throw new InvalidInputException("localizacaoTexto deve estar no formato POINT(longitude latitude).");
        }
    }
}