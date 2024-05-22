package com.alura.cokke.Literalura.repository;

import com.alura.cokke.Literalura.model.Autor;
import com.alura.cokke.Literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    @Query("SELECT a FROM Autor a WHERE a.nombre != 'Anonymous'")
    List<Autor> verAutores();

    @Query("SELECT a FROM Autor a WHERE a.nombre != 'Anonymous' AND 0 != a.nacimiento AND a.nacimiento <= :fecha AND a.muerte >= :fecha")
    List<Autor> verAutoresVivos(int fecha);

    List<Libro> findLibrosByIdiomasContains(String idioma);
}
