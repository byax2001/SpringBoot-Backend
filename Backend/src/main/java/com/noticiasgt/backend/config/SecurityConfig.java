package com.noticiasgt.backend.config;

import com.noticiasgt.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org. springframework. security. config. http. SessionCreationPolicy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //Con esta configuración solo sera posible acceder a los endpoints de /auth sin autenticación
        // y el resto de endpoints requieren autenticación
        return http.csrf(csrf -> csrf.disable()) // Desactiva CSRF
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/auth/**").permitAll() // Endpoints abiertos
                                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll() // Permitir solicitudes OPTIONS para evitar los Cors
                                .anyRequest().authenticated() // Otros requieren autenticación
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Desactiva sesiones
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean //Todos los metodos con @Bean deben de ser publicos
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Usamos AuthenticationConfiguration para obtener el AuthenticationManager
        return authenticationConfiguration.getAuthenticationManager();
    }



}
