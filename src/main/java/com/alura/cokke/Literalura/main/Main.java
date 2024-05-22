package com.alura.cokke.Literalura.main;

import com.alura.cokke.Literalura.model.Autor;
import com.alura.cokke.Literalura.model.Datos;
import com.alura.cokke.Literalura.model.DatosLibros;
import com.alura.cokke.Literalura.model.Libro;
import com.alura.cokke.Literalura.repository.LibroRepository;
import com.alura.cokke.Literalura.service.ConsumoAPI;
import com.alura.cokke.Literalura.service.ConvierteDatos;

import java.util.Scanner;
import java.util.List;

public class Main {

    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI api= new ConsumoAPI();
    private final String URL = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository rep;

    public Main(LibroRepository repository){
        this.rep = repository;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {

            System.out.println("""
                
                Eliga una opción
                
                1 - Buscar un libro por título
                2 - Mostrar libros registrados
                3 - Mostrar autores registrados
                4 - Mostrar autores vivos en determinado año
                5 - Mostrar libros por idioma
                0 - Salir
                """);

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                continue;
            }

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> mostrarLibros();
                case 3 -> mostrarAutores();
                case 4 -> mostrarAutoresVivos();
                case 5 -> mostrarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando el sistema...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }

        }
    }

    private void buscarLibro() {
        System.out.println("Escribe el título del libro: ");
        String titulo = sc.nextLine().trim();
        var json = api.obtenerDatos(URL + "?search=" + titulo.replace(" ", "%20"));
        var datos = conversor.obtenerDatos(json, Datos.class);
        if (!datos.libros().isEmpty()) {
            int contador = 1;
            for (DatosLibros l : datos.libros()) {
                System.out.println(contador + ". " + l);
                contador++;
            }
            System.out.println("\nIngrese el número del libro que desea agregar: ");
            try {
                int numLibro = Integer.parseInt(sc.nextLine().trim());
                if (numLibro > 0 && numLibro <= datos.libros().size()) {
                    Libro l = new Libro(datos.libros().get(numLibro - 1));
                    System.out.println(l);
                    rep.save(l);
                    System.out.println("Libro añadido correctamente");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
            }
        } else {
            System.out.println("No se ha encontrado el libro");
        }
    }

    private void mostrarLibros() {
        List<Libro> libros = rep.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se han registrado libros");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void mostrarAutores() {
        List<Autor> autores = rep.verAutores();
        autores.forEach(System.out::println);
    }

    private void mostrarAutoresVivos() {
        System.out.println("Ingrese año: ");
        var fecha = Integer.parseInt(sc.nextLine().trim());
        List<Autor> autoresVivos = rep.verAutoresVivos(fecha);
        if (!autoresVivos.isEmpty()) {
            for (Autor a : autoresVivos) {
                if (a.getNacimiento() != 0 && a.getMuerte() != 0) {
                    System.out.println(a);
                }
            }
        } else {
            System.out.println("No se encontraron autores vivos en el año " + fecha);
        }
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("""
            Ingrese el idioma deseado
            
            en - inglés
            es - espanol
            pt - portugués
            it - italiano
            ja - japones
            ru - ruso
            de - aleman
            """);
        var idioma = sc.nextLine();
        List<Libro> librosPorIdioma = rep.findLibrosByIdiomasContains(idioma);
        if (!librosPorIdioma.isEmpty()) {
            librosPorIdioma.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron libros en este idioma");
        }
    }
}


