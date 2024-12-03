package com.alura.Literalura.models;

import com.alura.Literalura.dto.BookDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @Column(unique = true)
    private String title;

    @ManyToOne()
    private Author author;
    private String language;
    @JsonAlias({"download_count"})
    private Integer downloads;

    public Book(){}
    public Book(BookDTO bookDto){
        this.title=bookDto.title();
        this.author=new Author(bookDto.authorsList().get(0).name(),
                bookDto.authorsList().get(0).birthYear(),
                bookDto.authorsList().get(0).deathYear());
        this.language=bookDto.languages().get(0);
        this.downloads=bookDto.downloads();
    }

    public Book(String title,Author authors,  String languages, Integer downloads) {
        this.author = authors;
        this.title = title;
        this.language = languages;
        this.downloads = downloads;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  "Book's Title:" + title + '\n' +
                "Author: " + author.getName().replace("+", " ")+'\n'+
                "Languages: " + language +'\n'+
                "Number of downloads: " + downloads +'\n';
    }
}
