package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.NivelAcessoDTO;
import com.riquetti.ProjetoIntegrador.entity.NivelAcesso;
import com.riquetti.ProjetoIntegrador.mapper.NivelAcessoMapper;
import com.riquetti.ProjetoIntegrador.repository.NivelAcessoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NivelAcessoService {

    private final NivelAcessoRepository nivelAcessoRepository;
    private final NivelAcessoMapper mapper = NivelAcessoMapper.INSTANCE;

    public NivelAcessoService(NivelAcessoRepository nivelAcessoRepository) {
        this.nivelAcessoRepository = nivelAcessoRepository;
    }

    public NivelAcessoDTO findById(Long idNivelAcesso) {
        NivelAcesso nivelAcesso = nivelAcessoRepository.findById(idNivelAcesso);
        return mapper.toDTO(nivelAcesso);
    }

    public List<NivelAcessoDTO> findAll() {
        List<NivelAcesso> niveisAcesso = nivelAcessoRepository.findAll();
        return niveisAcesso.stream().map(mapper::toDTO).toList();
    }

    public void save(NivelAcessoDTO nivelAcessoDTO) {
        NivelAcesso nivelAcesso = mapper.toEntity(nivelAcessoDTO);
        nivelAcessoRepository.save(nivelAcesso);
    }

    public void update(NivelAcessoDTO nivelAcessoDTO) {
        NivelAcesso nivelAcesso = mapper.toEntity(nivelAcessoDTO);
        nivelAcessoRepository.update(nivelAcesso);
    }

    public void delete(Long idNivelAcesso) {
        nivelAcessoRepository.delete(idNivelAcesso);
    }

}
