package com.alura.Literalura.Service;

import com.alura.Literalura.models.Author;
import com.alura.Literalura.models.Book;
import com.alura.Literalura.repository.AuthorRepository;
import com.alura.Literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

    @Service
    public class BookService {
        @Autowired
        BookRepository bookRepository;
        @Autowired
        AuthorRepository authorRepository;

        public List<Book> getAll(){
            return bookRepository.findAll();
        }

        public Optional<List<Book>> getBooksInLanguage(String language){
            return bookRepository.findByLanguage(language);
        }

        public void saveBook(Book book){
            Author author= book.getAuthor();
            Optional<Author> authorOpt=authorRepository.findAuthorByName(author.getName());
            if(authorOpt.isPresent()){
                book.setAuthor(authorOpt.get());
            }else{
                authorRepository.save(author);
                book.setAuthor(author);
            }
            bookRepository.save(book);
        }

        public  List<Book> topFiveBooks(){
            return bookRepository.findAll().stream()
                    .sorted(Comparator.comparing(Book::getDownloads).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
        }

        public  boolean isExist(String title){
            List<Book> books=getAll();
            for(Book b:books){
                if(b.getTitle().toLowerCase().contains(title.toLowerCase())){
                    return true;
                }
            }
            return false;
        }

    }


