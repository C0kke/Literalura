package com.alura.cokke.Literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String titulo;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores = new ArrayList<Autor>();
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "idioma")
    private List<String> idiomas = new ArrayList<String>();

    public Libro(){}

    public Libro(DatosLibros data) {
        this.titulo = data.titulo();
        this.idiomas = data.idiomas();
        this.autores = data.autores().stream()
                .map(d -> {
                    Autor autor = new Autor(d);
                    autor.setLibro(this);
                    return autor;
                })
                .collect(Collectors.toList());
    }

    public Long getID() {
        return ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        autores.forEach(a -> a.setLibro(this));
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public String verAutores() {
        return "";
    }

    @Override
    public String toString() {
        return "\n" + getTitulo() + "\nAutores: " + autores + "\nIdiomas: " + getIdiomas();
    }
}
