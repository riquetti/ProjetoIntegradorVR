package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioViasDTO;
import com.riquetti.ProjetoIntegrador.mapper.ComercioRaioViasMapper;
import com.riquetti.ProjetoIntegrador.repository.ComercioRaioViasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComercioRaioViasService {

    private final ComercioRaioViasRepository repository;
    private final ComercioRaioViasMapper mapper;

    public ComercioRaioViasService(ComercioRaioViasRepository repository, ComercioRaioViasMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ComercioRaioViasDTO> getIdVias(Long idComercio) {
        return repository.findByComercioId(idComercio)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ComercioRaioViasDTO> getAllVias() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar o comércio por ID com diferentes distâncias
    public List<ComercioRaioViasDTO> findByComercioIdDistancia(Long idComercio) {
        // Chama o repositório passando apenas o idComercio
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



}
