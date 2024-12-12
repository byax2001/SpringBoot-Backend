package com.noticiasgt.backend.services;
import com.noticiasgt.backend.models.Usuario;
import com.noticiasgt.backend.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Boolean agregarUsuario(Usuario usuario) {
        try{
            //Si usuario ya existe
            // El findByUsuario es un metodo que se creo en el UsuarioRepository con la funcion @Query
            if (usuarioRepository.findByUsuario(usuario.getUsuario()) != null) {
                return false;
            }
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Usuario login(String usuario, String contrasena) {
        try{
            return usuarioRepository.login(usuario, contrasena);
        }catch (Exception e){
            return null;
        }
    }
}