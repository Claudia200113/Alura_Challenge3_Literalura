package com.alura.Literalura;

import com.alura.Literalura.Main.Main;
import com.alura.Literalura.Service.AuthorService;
import com.alura.Literalura.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	BookService bookService;
	@Autowired
	AuthorService authorService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(bookService, authorService);
		main.ShowMenu();
	}


}
