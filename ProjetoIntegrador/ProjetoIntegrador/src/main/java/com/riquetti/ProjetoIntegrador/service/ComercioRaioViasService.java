package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioViasDTO;
import com.riquetti.ProjetoIntegrador.mapper.ComercioRaioViasMapper;
import com.riquetti.ProjetoIntegrador.repository.ComercioRaioViasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócios relacionada a comércios, raios de ação e dados OSM.
 */
@Service
public class ComercioRaioViasService {

    private final ComercioRaioViasRepository repository;
    private final ComercioRaioViasMapper mapper;

    /**
     * Construtor da classe que injeta as dependências do repositório e do mapper.
     *
     * @param repository O repositório ComercioRaioViasRepository responsável por acessar os dados de comércios em um raio de vias.
     * @param mapper O mapeador ComercioRaioViasMapper responsável por converter as entidades de domínio em DTOs e vice-versa.
     */
    public ComercioRaioViasService(ComercioRaioViasRepository repository, ComercioRaioViasMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Recupera vias relacionadas ao ID do comércio.
     *
     * @param idComercio ID do comércio a ser buscado. Deve ser um número inteiro maior que 0.
     * @return Lista de DTOs de vias correspondentes ao ID do comércio.
     * @throws IllegalArgumentException se o ID do comércio for nulo, menor ou igual a 0, ou não for um número válido.
     */
    public List<ComercioRaioViasDTO> getIdVias(Long idComercio) {
        validateIdComercio(idComercio);
        return repository.findByComercioId(idComercio)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera todas as vias.
     *
     * @return Lista de DTOs de todas as vias.
     */
    public List<ComercioRaioViasDTO> getAllVias() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera vias relacionadas ao ID do comércio com diferentes distâncias.
     *
     * @param idComercio ID do comércio a ser buscado. Deve ser um número inteiro maior que 0.
     * @return Lista de DTOs de vias correspondentes ao ID do comércio e suas distâncias.
     * @throws IllegalArgumentException se o ID do comércio for nulo, menor ou igual a 0, ou não for um número válido.
     */
    public List<ComercioRaioViasDTO> findByComercioIdDistancia(Long idComercio) {
        validateIdComercio(idComercio);
        return repository.findByComercioIdDistancia(idComercio)
                .stream()
                .map(entity -> new ComercioRaioViasDTO(
                        entity.getIdComercio(),
                        entity.getNome(),
                        entity.getDescricao(),
                        entity.getRaioAcaoMetros(),
                        entity.getComprimentoTotalPonderado(),
                        entity.getComprimento500(),
                        entity.getComprimento1000(),
                        entity.getComprimento1500(),
                        entity.getComprimento2000(),
                        entity.getComprimento2500(),
                        entity.getAcessibilidade(),
                        entity.getLocalizacao()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Recupera todas as vias e suas respectivas distâncias.
     *
     * @return Lista de DTOs de todas as vias e suas distâncias.
     */
    public List<ComercioRaioViasDTO> findAllViasDistancia() {
        return repository.findAllViasDistancia()
                .stream()
                .map(entity -> new ComercioRaioViasDTO(
                        entity.getIdComercio(),
                        entity.getNome(),
                        entity.getDescricao(),
                        entity.getRaioAcaoMetros(),
                        entity.getComprimentoTotalPonderado(),
                        entity.getComprimento500(),
                        entity.getComprimento1000(),
                        entity.getComprimento1500(),
                        entity.getComprimento2000(),
                        entity.getComprimento2500(),
                        entity.getAcessibilidade(),
                        entity.getLocalizacao()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Valida o ID do comércio.
     *
     * @param idComercio ID do comércio a ser validado.
     * @throws IllegalArgumentException se o ID do comércio for nulo, menor ou igual a 0, ou não for um número válido.
     */
    private void validateIdComercio(Long idComercio) {
        if (idComercio == null || idComercio <= 0) {
            throw new IllegalArgumentException("O ID do comércio deve ser um número inteiro maior que 0.");
        }
    }
}

