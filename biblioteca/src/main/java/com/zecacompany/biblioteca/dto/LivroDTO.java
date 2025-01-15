package com.zecacompany.biblioteca.dto;

public record LivroDTO(Long id, String titulo, String autor, String isbn, Integer quantidadeDisponivel) {
}
