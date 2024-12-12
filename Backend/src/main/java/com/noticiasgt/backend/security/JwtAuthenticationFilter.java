package com.noticiasgt.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noticiasgt.backend.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    //Intellij Da la opcion de implementar todos los metodos abstractos si se da click en la clase y se presiona Alt+Enter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                            //Esta funcion se declaro abajo
        final String token = getTokenFromRequest(request);
        final String usuario;

        //Json que se mostrara en caso de error junto con el status 403
        if (token==null || token.isEmpty())
        {
            filterChain.doFilter(request, response);
            return;
        }
                            //Funciones que se crearon en JwtService
        usuario = jwtService.getUsernameFromToken(token);

        if(usuario!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            final UserDetails userDetails = userDetailsService.loadUserByUsername(usuario);
            if (jwtService.isTokenValid(token, userDetails))
            {
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            
           
        }

        filterChain.doFilter(request,response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        // Verifica si el encabezado está presente, tiene contenido y comienza con "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Extrae el token sin "Bearer "
        }

        return null; // Si no cumple las condiciones, devuelve null
    }
    //Funcion para verificar que el formato del token sea correcto ya que debe de tener 3 partes
    private boolean isJwtFormatValid(String token) {
        if (token == null || token.isEmpty()) {
            return false; // El token no puede estar vacío o nulo
        }
        String[] parts = token.split("\\.");
        return parts.length == 3; // Un JWT debe tener exactamente tres partes
    }

}
