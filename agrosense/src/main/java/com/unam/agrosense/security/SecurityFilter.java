package com.unam.agrosense.security;

import com.unam.agrosense.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("Procesando solicitud: " + request.getMethod() + " " + request.getRequestURI());

        // Intentar obtener el token de la cabecera
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            // Si no existe la cabecera, buscar en las cookies
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("authToken".equals(cookie.getName())) {
                        authHeader = "Bearer " + cookie.getValue();
                        break;
                    }
                }
            }
        }
        System.out.println("Header de autorización: " + (authHeader != null ? "Presente" : "Ausente"));

        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            System.out.println("Token extraído del header o cookie");
            try {
                var userName = tokenService.getSubject(token);
                System.out.println("Subject extraído del token: " + (userName != null ? userName : "null"));
                if(userName != null){
                    var usuario = usuarioRepository.findByUsername(userName);
                    System.out.println("Usuario encontrado en la base de datos: " + (usuario != null ? "Sí" : "No"));
                    if(usuario != null){
                        System.out.println("Rol del usuario: " + usuario.getRol().name());
                        var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                                usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("Autenticación establecida en el SecurityContextHolder");
                    }
                }
            } catch (Exception e){
                System.out.println("Error al procesar el token: " + e.getMessage());
            }
        }
        filterChain.doFilter(request,response);
    }

}
