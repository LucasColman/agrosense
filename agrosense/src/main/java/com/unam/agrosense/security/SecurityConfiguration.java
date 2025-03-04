package com.unam.agrosense.security;


import com.unam.agrosense.model.usuario.Rol;
import com.unam.agrosense.model.usuario.Usuario;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {

                    //permitir acceso a estos endpoints sin autenticación
                    req.requestMatchers(HttpMethod.POST, "/auth","/usuarios/registro").permitAll();

                    //permitir acceso a estos endpoints solo a usuarios con rol ADMIN
                    req.requestMatchers(HttpMethod.POST,
                            "/sensores/store",
                            "/actuadores/store",
                            "/tipos-sensores/store",
                            "/tipos-actuadores/store",
                            "/sensores/**",
                            "/usuarios").permitAll();

                    req.requestMatchers(HttpMethod.PUT,
                            "/sensores/edit/**",
                            "/actuadores/**",
                            "/tipos-sensores/**",
                            "/tipos-actuadores/**",
                            "/usuarios/**").permitAll();

                    req.requestMatchers(HttpMethod.DELETE,
                            "/sensores/**",
                            "/actuadores/**",
                            "/tipos-sensores/**",
                            "/tipos-actuadores",
                            "/usuarios/**").permitAll();

                    req.requestMatchers(HttpMethod.GET,"/usuarios/**").permitAll();

                    // Endpoints para administradores y usuarios
                    req.requestMatchers(HttpMethod.GET,
                            "/sensores/**",
                            "/actuadores",
                            "/tipos-sensores",
                            "/tipos-actuadores").permitAll();

                    //Todos los demás endpoints requieren autenticación
                    req.anyRequest().authenticated();

                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
