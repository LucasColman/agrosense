package com.unam.agrosense.services;


import com.unam.agrosense.model.usuario.Rol;
import com.unam.agrosense.model.usuario.UsuarioResponseDto;
import com.unam.agrosense.model.usuario.Usuario;
import com.unam.agrosense.model.usuario.UsuarioDto;
import com.unam.agrosense.repository.UsuarioRepository;
import com.unam.agrosense.security.SecurityConfiguration;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final SecurityConfiguration securityConfiguration;

    public UsuarioService(UsuarioRepository usuarioRepository, SecurityConfiguration securityConfiguration) {
        this.usuarioRepository = usuarioRepository;
        this.securityConfiguration = securityConfiguration;
    }


    //CREAR USUARIO
    @Transactional
    public UsuarioResponseDto crearUsuario(UsuarioDto usuarioDto){
        PasswordEncoder bCrypt = securityConfiguration.passwordEncoder();

        if (usuarioRepository.existsByEmail(usuarioDto.email())) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario usuario= new Usuario();
        usuario.setUsername(usuarioDto.username());
        usuario.setEmail(usuarioDto.email());
        usuario.setRol(Rol.USER);
        usuario.setPassword(bCrypt.encode(usuarioDto.password()));
        usuarioRepository.save(usuario);

        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRol()
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
                usuario.getEmail(),
                usuario.getRol()
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
                usuario.getEmail(),
                usuario.getRol()
        );
    }
    // BUSCAR TODOS LOS USUARIOS
    public List<UsuarioResponseDto> buscarTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAllByActivoTrue();
        return usuarios.stream()
                .map(usuario -> new UsuarioResponseDto(
                        usuario.getId(),
                        usuario.getUsername(),
                        usuario.getEmail(),
                        usuario.getRol()
                ))
                .toList();
    }

    public UsuarioResponseDto asignarAdmin( Long id) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() ->  new EntityNotFoundException("Usuario no encontrado"));

        if (usuario.getRol() == Rol.ADMIN) {
            throw new RuntimeException("El usuario ya es ADMIN");
        }

        usuario.setRol(Rol.ADMIN);
        usuarioRepository.save(usuario);

        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }



}
