package com.alura.Literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO(
        String title,
        @JsonAlias("authors") List<AuthorDTO> authorsList,
        List<String> languages,
        @JsonAlias("download_count") int downloads) {
}
