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
        //El SecurityContextHolder es un objeto que se encarga de almacenar los detalles de autenticación del usuario actual
        // y se puede acceder desde cualquier parte de la aplicación
        // El getContext() devuelve el contexto de seguridad actual de la aplicación.
        // El getAuthentication() devuelve un objeto de autenticación que representa al usuario actualmente autenticado.
        // El ==null verifica si el usuario no esta autenticado para que no se pueda acceder a la aplicacion
        // dos veces con el mismo usuario
        if(usuario!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            //Se obtiene el usuario y se verifica si el token es valido
            //Se carga el usuario en un objeto UserDetails
            final UserDetails userDetails = userDetailsService.loadUserByUsername(usuario);
            if (jwtService.isTokenValid(token, userDetails))
            {
                // Se crea un objeto UsernamePasswordAuthenticationToken con el usuario, las credenciales y los roles
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, // Las credenciales sirven para autenticar al usuario, pero en este caso no se necesitan
                    userDetails.getAuthorities());
                //Se crea un objeto WebAuthenticationDetailsSource para obtener los detalles de la autenticacion
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Se establece el objeto UsernamePasswordAuthenticationToken en el contexto de seguridad
                //para que Spring Security pueda acceder a él
                //El setAuthentication() establece el objeto de autenticación actual en el contexto de seguridad

                //Basicamente con esto autorizamos al usuario a acceder a los endpoins protegidos por Spring Security
                //Al verificarse que el token es valido
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            
           
        }
        //El doFilter() llama al siguiente filtro en la cadena de filtros de Spring Security
        //para que la solicitud continúe su procesamiento
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
