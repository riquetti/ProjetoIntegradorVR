package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.ConcorrenteDTO;
import com.riquetti.ProjetoIntegrador.mapper.ConcorrenteMapper;
import com.riquetti.ProjetoIntegrador.repository.ConcorrenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConcorrenteService {
    private final ConcorrenteRepository repository;
    private final ConcorrenteMapper mapper = ConcorrenteMapper.INSTANCE;

    public ConcorrenteService(ConcorrenteRepository repository) {
        this.repository = repository;
    }

    public List<ConcorrenteDTO> getConcorrentesByRaio(Long idComercio, Long raioMetros) {
        return repository.findByRaio(idComercio, raioMetros)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
