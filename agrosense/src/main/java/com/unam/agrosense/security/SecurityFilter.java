package com.unam.agrosense.security;

import com.unam.agrosense.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Obtener el token del header
        var authHeader = request.getHeader("Authorization");
        // Verificar si el token es valido
        if(authHeader != null){
            var token = authHeader.replace("Bearer ", "");
            System.out.println("TOKEN " + token);
            try {
                var userName = tokenService.getSubject(token);
                if(userName != null){
                    var usuario = usuarioRepository.findByUsername(userName);
                    var authentication = new UsernamePasswordAuthenticationToken(usuario,null,
                            usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (Exception e){
                System.out.println("Error al procesar el token: " + e.getMessage());
            }



        }
        filterChain.doFilter(request,response);
    }
}
