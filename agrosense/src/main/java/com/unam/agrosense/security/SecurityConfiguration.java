package com.unam.agrosense.security;


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
                            "/sensores",
                            "/actuadores",
                            "/tipos-sensores",
                            "/tipos-actuadores",
                            "/usuarios").hasRole("ADMIN");

                    req.requestMatchers(HttpMethod.PUT,
                            "/sensores/**",
                            "/actuadores/**",
                            "/tipos-sensores/**",
                            "/tipos-actuadores/**",
                            "/usuarios/**").hasRole("ADMIN");

                    req.requestMatchers(HttpMethod.DELETE,
                            "/sensores/**",
                            "/actuadores/**",
                            "/tipos-sensores/**",
                            "/tipos-actuadores/**",
                            "/usuarios/**").hasRole("ADMIN");

                    req.requestMatchers(HttpMethod.GET,"/usuarios/**").hasAnyRole("ADMIN");

                    // Endpoints para administradores y usuarios
                    req.requestMatchers(HttpMethod.GET,
                            "/sensores/**",
                            "/actuadores",
                            "/tipos-sensores",
                            "/tipos-actuadores").hasAnyRole("ADMIN", "USER");

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
