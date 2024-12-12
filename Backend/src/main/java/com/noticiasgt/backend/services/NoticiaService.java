package com.noticiasgt.backend.services;
import com.noticiasgt.backend.models.Noticia;
import com.noticiasgt.backend.repositories.NoticiaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoticiaService {
    private final NoticiaRepository noticiaRepository;

    public NoticiaService(NoticiaRepository noticiaRepository) {
        this.noticiaRepository = noticiaRepository;
    }

    public Noticia agregarNoticia(Noticia noticia) {
        return noticiaRepository.save(noticia);
    }
    //Obtener una noticia por su id
    public Noticia obtenerNoticia(Long id) {
        return noticiaRepository.findById(id).orElse(null);
    }
    //Obtener noticias por categoria
    public List<Noticia> obtenerNoticiasPorCategoria(Long categoriaId) {
        return noticiaRepository.findByCategoria(categoriaId);
    }

    //Obtener todas las noticias  y retornarlas como un array de objetos
    public List<Noticia> obtenerNoticias() {
        return noticiaRepository.findAll();
    }
}