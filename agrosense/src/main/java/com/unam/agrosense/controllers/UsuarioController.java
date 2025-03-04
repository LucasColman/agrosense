package com.unam.agrosense.controllers;

import com.unam.agrosense.model.usuario.Rol;
import com.unam.agrosense.model.usuario.Usuario;
import com.unam.agrosense.model.usuario.UsuarioDto;
import com.unam.agrosense.model.usuario.UsuarioResponseDto;
import com.unam.agrosense.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
    }

    //REGISTRAR USUARIO
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDto> registrarUsuario(@RequestBody @Valid UsuarioDto usuarioDto, UriComponentsBuilder uriComponentsBuilder){
        UsuarioResponseDto usuarioResponseDto = usuarioService.crearUsuario(usuarioDto);

        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuarioResponseDto.id()).toUri();
        return ResponseEntity.created(url).body(usuarioResponseDto);
    }

    //ACTUALIZAR USUARIO
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioDto usuarioDto){
        UsuarioResponseDto usuarioResponseDto = usuarioService.actualizarUsuario(id, usuarioDto);
        return ResponseEntity.ok(usuarioResponseDto);
    }
    //ELIMINAR USUARIO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    //OBTENER USUARIO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> obtenerUsuario(@PathVariable Long id){
        UsuarioResponseDto usuarioResponseDto = usuarioService.buscarUsuario(id);
        return ResponseEntity.ok(usuarioResponseDto);
    }

    //OBTENER USUARIOS
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> obtenerUsuarios(){
        List<UsuarioResponseDto> usuarios = usuarioService.buscarTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/asignar-admin")
    public ResponseEntity<?> asignarAdmin(@PathVariable Long id) {
        UsuarioResponseDto usuario = usuarioService.asignarAdmin(id);


        return ResponseEntity.ok("✅ El usuario ahora es ADMIN");
    }
}

