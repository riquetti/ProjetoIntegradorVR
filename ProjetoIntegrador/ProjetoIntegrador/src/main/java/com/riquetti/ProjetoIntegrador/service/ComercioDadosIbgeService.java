package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.ComercioDadosIbgeDTO;
import com.riquetti.ProjetoIntegrador.mapper.ComercioDadosIbgeMapper;
import com.riquetti.ProjetoIntegrador.repository.ComercioDadosIbgeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComercioDadosIbgeService {
    private final ComercioDadosIbgeRepository repository;
    private final ComercioDadosIbgeMapper mapper = ComercioDadosIbgeMapper.INSTANCE;

    public ComercioDadosIbgeService(ComercioDadosIbgeRepository repository) {
        this.repository = repository;
    }

    // Método para buscar localização por ID com raio de ação padrão
    public List<ComercioDadosIbgeDTO> getLocalizacaoComercioById(Long idComercio) {
        return repository.findByIdComercio(idComercio)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar localização por ID com raio de ação personalizado (raio em metros)
    public List<ComercioDadosIbgeDTO> getLocalizacaoComercioByIdWithRaio(Long idComercio, double raioAcaoMetros) {
        return repository.findByIdComercioWithRaio(idComercio, raioAcaoMetros)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar todas as localizações com raio de ação padrão
    public List<ComercioDadosIbgeDTO> getAllLocalizacaoComercio() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar todas as localizações com raio de ação personalizado (raio em metros)
    public List<ComercioDadosIbgeDTO> getAllLocalizacaoComercioWithRaio(double raioAcaoMetros) {
        return repository.findAllWithRaio(raioAcaoMetros)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

}
