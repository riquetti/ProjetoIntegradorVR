package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.ComercioDadosIbgeDTO;
import com.riquetti.ProjetoIntegrador.entity.ComercioDadosIbge;
import com.riquetti.ProjetoIntegrador.exception.ResourceNotFoundException;
import com.riquetti.ProjetoIntegrador.mapper.ComercioDadosIbgeMapper;
import com.riquetti.ProjetoIntegrador.repository.ComercioDadosIbgeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar operações relacionadas aos dados de localização dos comércios
 * provenientes do IBGE.
 *
 * Este serviço utiliza o repositório para realizar operações de consulta
 * e mapeia os resultados para DTOs usando um mapper específico.
 */
@Service
public class ComercioDadosIbgeService {

    private final ComercioDadosIbgeRepository repository; // Repositório para acessar os dados dos comércios
    private final ComercioDadosIbgeMapper mapper = ComercioDadosIbgeMapper.INSTANCE; // Mapper para conversão entre entidades e DTOs

    /**
     * Construtor para injeção de dependência do repositório.
     *
     * @param repository O repositório de dados de comércios.
     */
    public ComercioDadosIbgeService(ComercioDadosIbgeRepository repository) {
        this.repository = repository;
    }

    /**
     * Método para buscar localização por ID com raio de ação padrão.
     * Lança uma exceção se o ID não for encontrado.
     *
     * @param idComercio ID do comércio.
     * @return Lista de DTOs de localização.
     */
    public List<ComercioDadosIbgeDTO> getLocalizacaoComercioById(Long idComercio) {
        if (idComercio == null || idComercio <= 0) {
            throw new IllegalArgumentException("O ID do comércio deve ser um número positivo e não nulo.");
        }
        List<ComercioDadosIbge> comercios = repository.findByIdComercio(idComercio);
        if (comercios.isEmpty()) {
            throw new ResourceNotFoundException("Comércio com ID " + idComercio + " não encontrado.");
        }
        return comercios.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Método para buscar localização por ID com raio de ação personalizado (raio em metros).
     * Lança uma exceção se o ID não for encontrado.
     *
     * @param idComercio ID do comércio.
     * @param raioAcaoMetros Raio de ação em metros.
     * @return Lista de DTOs de localização.
     */
    public List<ComercioDadosIbgeDTO> getLocalizacaoComercioByIdWithRaio(Long idComercio, Double raioAcaoMetros) {
        if (idComercio == null || idComercio <= 0) {
            throw new IllegalArgumentException("O ID do comércio deve ser um número positivo e não nulo.");
        }
        if (raioAcaoMetros == null || raioAcaoMetros <= 0) {
            throw new IllegalArgumentException("O raio de ação deve ser um número positivo e não nulo.");
        }

        List<ComercioDadosIbge> comercios = repository.findByIdComercioWithRaio(idComercio, raioAcaoMetros);
        if (comercios.isEmpty()) {
            throw new ResourceNotFoundException("Comércio com ID " + idComercio + " e raio " + raioAcaoMetros + " metros não encontrado.");
        }
        return comercios.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Método para buscar todas as localizações com raio de ação padrão.
     *
     * @return Lista de DTOs de localização.
     */
    public List<ComercioDadosIbgeDTO> getAllLocalizacaoComercio() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Método para buscar todas as localizações com raio de ação personalizado (raio em metros).
     *
     * @param raioAcaoMetros Raio de ação em metros.
     * @return Lista de DTOs de localização.
     */
    public List<ComercioDadosIbgeDTO> getAllLocalizacaoComercioWithRaio(Double raioAcaoMetros) {
        if (raioAcaoMetros == null || raioAcaoMetros <= 0) {
            throw new IllegalArgumentException("O raio de ação deve ser um número positivo e não nulo.");
        }

        return repository.findAllWithRaio(raioAcaoMetros)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Método para buscar localizações com base em um ponto e raio.
     *
     * @param pontoTexto Ponto de localização em formato texto (WKT).
     * @param raioAcaoMetros Raio de ação em metros.
     * @return Lista de DTOs de localização.
     */
    public List<ComercioDadosIbgeDTO> getLocalizacaoComercioByPontoAndRaio(String pontoTexto, Long raioAcaoMetros) {
        if (pontoTexto == null || pontoTexto.trim().isEmpty()) {
            throw new IllegalArgumentException("O ponto de localização não pode ser nulo ou vazio.");
        }

        // Validação do formato do ponto
        String regex = "^POINT\\s*\\((-?\\d+\\.\\d+)\\s+(-?\\d+\\.\\d+)\\)$";
        if (!pontoTexto.trim().matches(regex)) {
            throw new IllegalArgumentException("O ponto de localização deve estar no formato POINT(longitude latitude), por exemplo, POINT(-47.4148445 -22.5936527).");
        }

        if (raioAcaoMetros == null || raioAcaoMetros <= 0) {
            throw new IllegalArgumentException("O raio de ação deve ser um número positivo e não nulo.");
        }

        return repository.findByLocationAndRaio(pontoTexto, raioAcaoMetros)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

}
