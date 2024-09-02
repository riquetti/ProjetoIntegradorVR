package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.ComercioRaioAvenidaDTO;
import com.riquetti.ProjetoIntegrador.entity.ComercioRaioAvenida;
import com.riquetti.ProjetoIntegrador.mapper.ComercioRaioAvenidaMapper;
import com.riquetti.ProjetoIntegrador.repository.ComercioRaioAvenidaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComercioRaioAvenidaService {

    private final ComercioRaioAvenidaRepository repository;
    private final ComercioRaioAvenidaMapper mapper = ComercioRaioAvenidaMapper.INSTANCE;

    // Construtor para injeção de dependências
    public ComercioRaioAvenidaService(ComercioRaioAvenidaRepository repository) {
        this.repository = repository;
    }

    // Busca todos os registros
    public List<ComercioRaioAvenidaDTO> findAll() {
        List<ComercioRaioAvenida> entities = repository.findAllComercios();
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Busca os registros por id_comercio
    public List<ComercioRaioAvenidaDTO> getComerciosById(Long idComercio) {
        List<ComercioRaioAvenida> entities = repository.findComerciosById(idComercio);
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ComercioRaioAvenidaDTO getComercioByIdAndRaio(Long idComercio, Long raioAcaoMetros) {
        ComercioRaioAvenida entity = repository.findComercioByIdAndRaio(idComercio, raioAcaoMetros);
        return mapper.toDTO(entity);
    }

    // Busca registros filtrados pela localização
    public List<ComercioRaioAvenidaDTO> getPontoRaioAvenida(String localizacaoTexto) {
        List<ComercioRaioAvenida> entities = repository.findPontoRaioAvenida(localizacaoTexto);
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // Busca registros filtrados pela localização e raio de ação
    public List<ComercioRaioAvenidaDTO> getLocalizacaoRaioAvenida(String localizacaoTexto, double raioAcaopersolizado) {
        List<ComercioRaioAvenida> entities = repository.findLocalizacaoRaioAvenida(localizacaoTexto, raioAcaopersolizado);
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

}
