package com.alura.cokke.Literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String nombre;
    private int nacimiento;
    private int muerte;
    @ManyToOne(cascade = CascadeType.ALL)
    private Libro libro;

    public Autor(){}

    public Autor(DatosAutores data) {
        this.nombre = data.nombre();
        this.nacimiento = data.nacimiento();
        this.muerte = data.muerte();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(int nacimiento) {
        this.nacimiento = nacimiento;
    }

    public int getMuerte() {
        return muerte;
    }

    public void setMuerte(int muerte) {
        this.muerte = muerte;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Long getID() {
        return ID;
    }

    @Override
    public String toString() {
        return getNombre() + " (" + getNacimiento() + " - " + getMuerte() + ")";
    }
}
