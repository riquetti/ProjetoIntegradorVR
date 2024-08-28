package com.riquetti.ProjetoIntegrador.service;

import com.riquetti.ProjetoIntegrador.dto.UsuarioDTO;
import com.riquetti.ProjetoIntegrador.entity.Usuario;
import com.riquetti.ProjetoIntegrador.mapper.UsuarioMapper;
import com.riquetti.ProjetoIntegrador.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = UsuarioMapper.INSTANCE;
    }

    public UsuarioDTO findById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id);
        return usuarioMapper.toDto(usuario);
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public void save(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuarioRepository.save(usuario);
    }

    public void update(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuarioRepository.update(usuario);
    }

    public void delete(Integer id) {
        usuarioRepository.delete(id);
    }

}
