package com.unam.agrosense.security;

import com.unam.agrosense.model.usuario.Usuario;
import com.unam.agrosense.model.usuario.UsuarioResponseDto;
import com.unam.agrosense.security.DatosJWTtoken;
import com.unam.agrosense.security.LoginDto;
import com.unam.agrosense.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid LoginDto loginDto){
        System.out.println("Iniciando autenticación para usuario: " + loginDto.username());
        try{
            Authentication token = new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());

            System.out.println("Token creado, intentando autenticar...");

            var usuarioAutenticado = authenticationManager.authenticate(token);

            System.out.println("Usuario autenticado correctamente");

            Usuario usuario = (Usuario) usuarioAutenticado.getPrincipal();

            System.out.println("Principal obtenido, usuario: " + usuario.getUsername());
            System.out.println("Rol del usuario: " + usuario.getRol().name());

            String JWTtoken = tokenService.generarToken(usuario);

            System.out.println("Token JWT generado");

            var respuesta = new DatosJWTtoken(JWTtoken, usuario.getRol().name());

            System.out.println("Objeto respuesta creado: token=" + (JWTtoken.substring(0, 10) + "...") + ", rol=" + respuesta.rol());

            return ResponseEntity.ok(respuesta);

        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error durante la autenticación: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok("Logout exitoso");
    }
}
