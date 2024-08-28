package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.TipoComercioDTO;
import com.riquetti.ProjetoIntegrador.entity.TipoComercio;
import com.riquetti.ProjetoIntegrador.mapper.TipoComercioMapper;
import com.riquetti.ProjetoIntegrador.repository.TipoComercioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoComercioService {

    private final TipoComercioRepository tipoComercioRepository;
    private final TipoComercioMapper tipoComercioMapper;

    public TipoComercioService(TipoComercioRepository tipoComercioRepository) {
        this.tipoComercioRepository = tipoComercioRepository;
        this.tipoComercioMapper = TipoComercioMapper.INSTANCE;
    }

    public TipoComercioDTO findById(Long idTipoComercio) {
        TipoComercio tipoComercio = tipoComercioRepository.findById(idTipoComercio);
        return tipoComercioMapper.toDTO(tipoComercio);
    }

    public List<TipoComercioDTO> findAll() {
        return tipoComercioRepository.findAll().stream()
                .map(tipoComercioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void save(TipoComercioDTO tipoComercioDTO) {
        TipoComercio tipoComercio = tipoComercioMapper.toEntity(tipoComercioDTO);
        tipoComercioRepository.save(tipoComercio);
    }

    public void update(TipoComercioDTO tipoComercioDTO) {
        TipoComercio tipoComercio = tipoComercioMapper.toEntity(tipoComercioDTO);
        tipoComercioRepository.update(tipoComercio);
    }

    public void delete(Long idTipoComercio) {
        tipoComercioRepository.delete(idTipoComercio);
    }

}
