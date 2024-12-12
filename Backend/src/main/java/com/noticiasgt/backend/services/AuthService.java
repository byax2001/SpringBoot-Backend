package com.noticiasgt.backend.services;

import com.noticiasgt.backend.models.AuthResponse;
import com.noticiasgt.backend.models.LoginRequest;
import com.noticiasgt.backend.models.RegisterRequest;
import com.noticiasgt.backend.models.Usuario;
import com.noticiasgt.backend.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthResponse login(LoginRequest loginRequest) {
        try {
            String contrasena = passwordEncoder.encode(loginRequest.getContrasena());
            System.out.println("Contraseña encriptada Login: " + contrasena);
            System.out.println("contraseña: " + loginRequest.getContrasena());
            // Autenticar usuario, este metodo lanzara una excepcion si el usuario no es correcto por lo tanto parara la ejecucion
            // Este metodo va directamente a la base de datos a buscar el usuario y la contraseña
            //Este metodo fue configurado en ApplicationConfig.java
            /*
             * Para ser mas especifico fue configurado en el metodo authenticationProvider (ApplicationConfig.java)
             * La logica fue la siguiente, se configuro la busqueda del usuario en la base de datos a través del metodo userDetailsService
             * una vez obtenido el usuario se compara la contraseña, en este caso se sabe cual es la columna contraseña gracias a 
             * getPassword() presente en el Modelo Usuario, dicho modelo implementa "UserDetails" que tiene entre sus metodos abstractos 
             * getPassword() y getUsername() que son los que se utilizan para la autenticación
             */
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsuario(), loginRequest.getContrasena()
            ));
            
            
            UserDetails user = usuarioRepository.findByUsuario(loginRequest.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
            String token = jwtService.getToken(user);
            return AuthResponse.builder()
                .token(token)
                .build();
        } catch (Exception e) {
            System.out.println("Error de autenticación: " + e.getMessage());
            return AuthResponse.builder()
                .token("error")
                .build();
        }
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        try{
            // Crear usuario
            // Encriptar contraseña
            String contrasena = passwordEncoder.encode(registerRequest.getContrasena());
            System.out.println("Contraseña encriptada REgistro: " + contrasena);
            System.out.println("contraseña: " + registerRequest.getContrasena());
            Usuario user = Usuario.builder()
                .usuario(registerRequest.getUsuario())
                .contrasena(contrasena)
                .nombre(registerRequest.getNombre())
                .build();
            usuarioRepository.save(user);
            // usuarioRepository.save devolvera si se ejecuta correctamente un usuario si no devolvera un error
            //Builder funcionando
            System.out.println("Usuario registrado: " + user.getUsuario());
            // Retornar token
            return AuthResponse.builder().token(jwtService.getToken(user)).build();
        }catch(Exception e){
            return AuthResponse.builder().token("error").build();
        }
        
    }

}
