package com.alura.Literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorDTO(
        String name,
        @JsonAlias("birth_year") int birthYear,
        @JsonAlias("death_year") int deathYear){
}
