package com.alura.Literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchDTO(
        List<BookDTO> results) {
}
