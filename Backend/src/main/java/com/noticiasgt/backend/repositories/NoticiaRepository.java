package com.noticiasgt.backend.repositories;


import com.noticiasgt.backend.models.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    //Query para obtener noticias por categoria
    @Query("SELECT n FROM Noticia n WHERE n.categoria.id = ?1")
    List<Noticia> findByCategoria(Long categoriaId);
}