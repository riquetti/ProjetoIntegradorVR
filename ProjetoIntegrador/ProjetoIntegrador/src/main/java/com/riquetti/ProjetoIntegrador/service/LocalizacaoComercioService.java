package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.LocalizacaoComercioDTO;
import com.riquetti.ProjetoIntegrador.entity.LocalizacaoComercio;
import com.riquetti.ProjetoIntegrador.mapper.LocalizacaoComercioMapper;
import com.riquetti.ProjetoIntegrador.repository.LocalizacaoComercioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalizacaoComercioService {

    private final LocalizacaoComercioRepository repository;
    private final LocalizacaoComercioMapper mapper = LocalizacaoComercioMapper.INSTANCE;

    public LocalizacaoComercioService(LocalizacaoComercioRepository repository) {
        this.repository = repository;
    }

    public LocalizacaoComercioDTO getLocalizacaoComercioById(Long idComercio) {
        LocalizacaoComercio comercio = repository.findById(idComercio);
        return mapper.toDTO(comercio);
    }

    public List<LocalizacaoComercioDTO> getAllLocalizacaoComercios() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public void createLocalizacaoComercio(LocalizacaoComercioDTO dto) {
        LocalizacaoComercio comercio = mapper.toEntity(dto);
        repository.save(comercio);
    }

    public void updateLocalizacaoComercio(LocalizacaoComercioDTO dto) {
        LocalizacaoComercio comercio = mapper.toEntity(dto);
        repository.update(comercio);
    }

    public void deleteLocalizacaoComercio(Long idComercio) {
        repository.delete(idComercio);
    }

}
