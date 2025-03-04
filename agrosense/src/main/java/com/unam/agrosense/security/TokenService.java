package com.unam.agrosense.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.unam.agrosense.model.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String apiSecret;
    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("agrosense")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withClaim("rol", usuario.getRol().name())
                    .withClaim("authorities", usuario.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .withExpiresAt(getExpiresAt())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al generar el token", exception);
        }
    }

    //Verificar Token
    public String getSubject(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier =JWT.require(algorithm)
                    .withIssuer("agrosense")
                    .build()
                    .verify(token);
            return verifier.getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token invalido", exception);
        }
    }

    //Este metodo se encarga de generar un token con un tiempo de expiracion de 2 horas
    private Instant getExpiresAt() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}

