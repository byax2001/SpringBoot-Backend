package com.noticiasgt.backend.controllers;
import com.noticiasgt.backend.models.Categoria;
import com.noticiasgt.backend.services.CategoriaService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor //Inyeccion de dependencias y me Evita tener que escribir el constructor
public class CategoriaController {
    private final CategoriaService categoriaService;


    @GetMapping
    @CrossOrigin(origins = "*")
    public List<Categoria> obtenerCategorias() {
        //Si no se ha logeado se devolvera un status 403
        return categoriaService.obtenerCategorias();
    }
}