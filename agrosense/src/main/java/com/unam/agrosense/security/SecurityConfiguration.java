package com.unam.agrosense.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
//                        .requestMatchers("/login","/signup", "/css/**", "/js/**").permitAll()
//                        .requestMatchers("/auth","/usuarios/registro", "/usuarios/perfil").permitAll()
//                        .requestMatchers("/dashboard").hasAuthority("ADMIN")
//                        .requestMatchers("/").hasAnyAuthority("USER", "ADMIN")
//
//                        .requestMatchers(HttpMethod.GET, "/actuadores/**").hasAnyAuthority("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/actuadores/**").hasAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/actuadores/**").hasAuthority("ADMIN")
//
//                        .requestMatchers(HttpMethod.GET, "/dato-sensor/**").hasAnyAuthority("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/dato-sensor/**").hasAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/dato-sensor/**").hasAuthority("ADMIN")
//
//
//                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT no usa sesiones
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

