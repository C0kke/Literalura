package com.alura.cokke.Literalura;

import com.alura.cokke.Literalura.main.Main;
import com.alura.cokke.Literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;
	@Override
	public void run(String... args) throws Exception {
		Main principal = new Main(repository);
		principal.muestraElMenu();
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

}
