package com.noticiasgt.backend.controllers;
import com.noticiasgt.backend.models.RespuestaCorta;
import com.noticiasgt.backend.models.Usuario;
import com.noticiasgt.backend.services.UsuarioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @CrossOrigin(origins = "*")
    public RespuestaCorta agregarUsuario(@RequestBody Usuario usuario) {
        System.out.println("Agregando usuario");
        System.out.println(usuario.getUsuario());
        boolean resultado = usuarioService.agregarUsuario(usuario);
        if(resultado){
            //La respuesta corta sera la estructura del JSON que se espera en la respuesta
            return new RespuestaCorta(true,"Usuario agregado correctamente");
        } else {
            return new RespuestaCorta(false,"Usuario ya existe");
        }
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public Usuario login(@RequestBody Usuario usuario) {
        Usuario user = usuarioService.login(usuario.getUsuario(), usuario.getContrasena());
        if(user != null){
            return user;
        } else {
            user = new Usuario();
            user.setNombre("no encontrado");
            return user;
        }
    }
}