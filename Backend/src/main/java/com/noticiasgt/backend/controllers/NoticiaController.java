package com.noticiasgt.backend.controllers;
import com.noticiasgt.backend.models.Noticia;
import com.noticiasgt.backend.services.NoticiaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController

@RequestMapping("/noticias")
public class NoticiaController {
    private final NoticiaService noticiaService;

    public NoticiaController(NoticiaService noticiaService) {
        this.noticiaService = noticiaService;
    }

    @PostMapping
    public Noticia agregarNoticia(@RequestBody Noticia noticia) {
        return noticiaService.agregarNoticia(noticia);
    }

    //Obtener una noticia por su id
    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    public Noticia obtenerNoticia(@PathVariable Long id) {
        Noticia noticia = noticiaService.obtenerNoticia(id);
        if(noticia == null) {
            noticia = new Noticia();
            noticia.setTitulo("n/a");
            noticia.setDescripcion("n/a");
        }
        return noticia;
    }
    //Obtener noticias por categoria
    @GetMapping("/categoria/{categoriaId}")
    @CrossOrigin(origins = "*")
    public List<Noticia> obtenerNoticiasPorCategoria(@PathVariable Long categoriaId) {
        //Si no halla noticias, retornar un array vacio
        return noticiaService.obtenerNoticiasPorCategoria(categoriaId);
    }

    //Obtener todas las noticias
    @GetMapping
    @CrossOrigin(origins = "*")
    public List<Noticia> obtenerNoticias() {
        return noticiaService.obtenerNoticias();
    }
}