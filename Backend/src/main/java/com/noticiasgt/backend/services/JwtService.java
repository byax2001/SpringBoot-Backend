package com.noticiasgt.backend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import io. jsonwebtoken. security. Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {
    //userDetails es un objeto que contiene los datos del usuario que se logueo
    //UserDetails es una interfaz de Spring Security que complementa tambien al Model Usuario
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user ) ;
    }
    //La llave siempre debe de poseer una longitud de 256 bits
    final static String KEY_SECRET="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    private String getToken(HashMap<String, Object> extraClaims, UserDetails user) {
        System.out.println("Generando token");
        System.out.println(user.getUsername());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getKey() {
       byte[] keyBytes=Decoders.BASE64.decode(KEY_SECRET);
       return Keys.hmacShaKeyFor(keyBytes);
    }

        //Obtener el nombre de usuario del token
    public String getUsernameFromToken(String token) {
        //Claims es una clase de la libreria de JWT que se encarga de guardar los datos del token
        // en getSubject se obtiene el nombre de usuario que se guardo en el token
        // es el pedazo de información donde se guardo el nombre de usuario
        // al importar jsonwebtoken.Claims se puede usar el metodo getSubject
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        //Se hara una comparacion entre el nombre de usuario que se obtiene del token y el nombre de usuario que se obtiene de los userDetails
        //Se verifica que el token no haya expirado
        //Si el nombre de usuario es igual y el token no ha expirado se devuelve true
        // Por lo tanto el token es valido
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    //Obtener todos los claims del token que son los datos que se guardaron en el
    private Claims getAllClaims(String token)
    {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    //Es un metodo generico que espera un token y una funcion que se encargara de obtener un dato especifico de los claims
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token)
    {   
        // En getExpiration se obtiene la fecha de expiración del token
        //Al importar jsonwebtoken.Claims se puede usar el metodo getExpiration
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token)
    {
        //Devuelve true si la fecha de expiración del token es antes de la fecha actual
        return getExpiration(token).before(new Date());
    }
    

}
