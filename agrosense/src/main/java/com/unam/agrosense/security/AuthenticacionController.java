package com.unam.agrosense.security;

import com.unam.agrosense.model.usuario.Usuario;
import com.unam.agrosense.security.DatosJWTtoken;
import com.unam.agrosense.security.LoginDto;
import com.unam.agrosense.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid LoginDto loginDto){
        try{
            Authentication token = new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());
            var usuarioAutenticado = authenticationManager.authenticate(token);
            Usuario usuario = (Usuario) usuarioAutenticado.getPrincipal();
            String JWTtoken = tokenService.generarToken(usuario);
            return ResponseEntity.ok(new DatosJWTtoken(JWTtoken,usuario.getRol()));

        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error durante la autenticación: " + e.getMessage());
        }



    }
}
