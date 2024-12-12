package com.noticiasgt.backend.repositories;
import com.noticiasgt.backend.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuario(String usuario); 

    //Query de login
    @Query("SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.contrasena = :contrasena")
    Usuario login(@Param("usuario") String usuario, @Param("contrasena") String contrasena);
}
