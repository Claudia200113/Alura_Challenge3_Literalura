package com.alura.Literalura.repository;

import com.alura.Literalura.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    Optional<Author> findAuthorByName(String name);

    List <Author> findByBirthYearLessThanEqualAndDeathYearGreaterThan (int birthYear, int deathYear);

}
