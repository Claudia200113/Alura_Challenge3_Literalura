package com.alura.Literalura.Service;

import com.alura.Literalura.models.Author;
import com.alura.Literalura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public List<Author> getAllAuthors(){
        Optional<List<Author>> authors= Optional.of(authorRepository.findAll());
        return authors.orElse(null);
    }

    public List<Author> getAuthorsByYear(int year) {
        return authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThan(year,year);
    }
}
