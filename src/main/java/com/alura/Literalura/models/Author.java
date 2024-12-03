package com.alura.Literalura.models;

import com.alura.Literalura.dto.AuthorDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "author")
public class Author {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(unique = true)
        private String name;
        private Integer birthYear;
        private Integer deathYear;

        @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
        private List<Book> books;


        public Author(){}

        public Author(String name,Integer birthYear,Integer deathYear) {
            this.name = name;
            this.birthYear= birthYear;
            this.deathYear = deathYear;
        }
        public Author(AuthorDTO authorDto){
            this.name=authorDto.name();
            this.birthYear=authorDto.birthYear();
            this.deathYear=authorDto.deathYear();
        }

        @Override
        public String toString() {
            return "Author's name:"+name + '\n' +
                    "Brith year:"+birthYear +'\n'+
                    "Death year:"+deathYear+'\n' +
                    "Books:"+this.getBooks().stream()
                    .map(Book::getTitle)
                    .toList()+'\n';
        }

        public Integer getBirthYear() {
            return birthYear;
        }

        public void setBirthYear(Integer birthYear) {
            this.birthYear = birthYear;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getDeathYear() {
            return deathYear;
        }

        public void setDeathYear(Integer deathYear) {
            this.deathYear = deathYear;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public List<Book> getBooks() {
            return books;
        }

        public void setBooks(List<Book> books) {
            this.books = books;
        }

}

