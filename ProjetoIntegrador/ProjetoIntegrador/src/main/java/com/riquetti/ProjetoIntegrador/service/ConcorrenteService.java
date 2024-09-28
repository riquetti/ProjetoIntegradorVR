package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.ConcorrenteDTO;
import com.riquetti.ProjetoIntegrador.mapper.ConcorrenteMapper;
import com.riquetti.ProjetoIntegrador.repository.ConcorrenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por realizar operações relacionadas a concorrentes
 * dentro de um raio específico a partir de uma localização de comércio.
 *
 * Este serviço utiliza o repositório ConcorrenteRepository para
 * acessar os dados do banco e o ConcorrenteMapper para mapear
 * entidades de domínio para DTOs.
 */
@Service
public class ConcorrenteService {

    private final ConcorrenteRepository repository;
    private final ConcorrenteMapper mapper = ConcorrenteMapper.INSTANCE;

    /**
     * Construtor do ConcorrenteService.
     *
     * @param repository o repositório responsável pelo acesso aos dados dos concorrentes.
     */
    public ConcorrenteService(ConcorrenteRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca concorrentes dentro de um raio específico a partir da localização de um comércio.
     *
     * Este método realiza a validação dos parâmetros antes de chamar o repositório para buscar
     * os dados. Se os parâmetros forem válidos, os resultados são mapeados para DTOs
     * antes de serem retornados.
     *
     * @param idComercio o ID do comércio a partir do qual o raio será calculado.
     * @param raioMetros o raio em metros dentro do qual os concorrentes serão buscados.
     * @return uma lista de {@link ConcorrenteDTO} representando os concorrentes encontrados.
     * @throws IllegalArgumentException se idComercio ou raioMetros forem inválidos.
     */
    public List<ConcorrenteDTO> getConcorrentesByRaio(Long idComercio, Long raioMetros) {
        validateIdComercio(idComercio);
        validateRaioMetros(raioMetros);

        return repository.findByRaio(idComercio, raioMetros)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca concorrentes utilizando o raio de ação do comércio especificado.
     *
     * Este método realiza a validação do ID do comércio antes de chamar o repositório
     * para buscar os dados. Os resultados são mapeados para DTOs antes de serem retornados.
     *
     * @param idComercio o ID do comércio cujo raio de ação será utilizado para a busca.
     * @return uma lista de ConcorrenteDTO representando os concorrentes encontrados.
     * @throws IllegalArgumentException se idComercio for inválido.
     */
    public List<ConcorrenteDTO> findByRaioFromComercio(Long idComercio) {
        validateIdComercio(idComercio);

        return repository.findByRaioFromComercio(idComercio)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Valida o ID do comércio.
     *
     * Este método verifica se o ID do comércio é nulo ou não positivo. Caso o ID
     * não atenda aos critérios de validação, uma IllegalArgumentException
     * será lançada.
     *
     * @param idComercio o ID do comércio a ser validado.
     * @throws IllegalArgumentException se idComercio for nulo ou não for positivo.
     */
    private void validateIdComercio(Long idComercio) {
        if (idComercio == null || idComercio <= 0) {
            throw new IllegalArgumentException("O idComercio deve ser um número inteiro positivo.");
        }
    }

    /**
     * Valida o valor do raio em metros.
     *
     * Este método verifica se o valor do raio é nulo ou não positivo. Caso o valor
     * não atenda aos critérios de validação, uma IllegalArgumentException
     * será lançada.
     *
     * @param raioMetros o valor do raio em metros a ser validado.
     * @throws IllegalArgumentException se raioMetros for nulo ou não for positivo.
     */
    private void validateRaioMetros(Long raioMetros) {
        if (raioMetros == null || raioMetros <= 0) {
            throw new IllegalArgumentException("O raioMetros deve ser um número inteiro positivo.");
        }
    }
}



