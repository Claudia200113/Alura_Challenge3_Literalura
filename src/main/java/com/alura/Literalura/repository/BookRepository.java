package com.alura.Literalura.repository;

import com.alura.Literalura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<List<Book>> findByLanguage(String lan);
}
