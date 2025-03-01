package com.unam.agrosense.services;


import com.unam.agrosense.model.usuario.UsuarioResponseDto;
import com.unam.agrosense.model.usuario.Usuario;
import com.unam.agrosense.model.usuario.UsuarioDto;
import com.unam.agrosense.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    //CREAR USUARIO
    @Transactional
    public UsuarioResponseDto crearUsuario(UsuarioDto usuarioDto){

        if (usuarioRepository.existsByEmail(usuarioDto.email())) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario usuario= new Usuario();
        usuario.setUsername(usuarioDto.username());
        usuario.setEmail(usuarioDto.email());
        usuario.setPassword(usuarioDto.password());

        usuarioRepository.save(usuario);

        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail()
        );
    }
    // ACTUALIZAR USUARIO
    @Transactional
    public UsuarioResponseDto actualizarUsuario(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() ->  new EntityNotFoundException("Usuario no encontrado"));

        usuario.setUsername(usuarioDto.username());
        usuario.setEmail(usuarioDto.email());
        usuario.setPassword(usuarioDto.password());

        usuarioRepository.save(usuario);

        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail()
        );

    }
    // ELIMINAR USUARIO
    @Transactional
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() ->  new EntityNotFoundException("Usuario no encontrado"));
        usuarioRepository.softDelete(id);

    }
    // BUSCAR USUARIO
    public UsuarioResponseDto buscarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() ->  new EntityNotFoundException("Usuario no encontrado"));
        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail()
        );
    }
    // BUSCAR TODOS LOS USUARIOS
    public List<UsuarioResponseDto> buscarTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAllByActivoTrue();
        return usuarios.stream()
                .map(usuario -> new UsuarioResponseDto(
                        usuario.getId(),
                        usuario.getUsername(),
                        usuario.getEmail()
                ))
                .toList();
    }
}
