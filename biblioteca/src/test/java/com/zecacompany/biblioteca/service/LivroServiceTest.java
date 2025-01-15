package com.zecacompany.biblioteca.service;

import com.zecacompany.biblioteca.domain.Livro;
import com.zecacompany.biblioteca.repository.LivroRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarLivro_DeveSalvarLivroQuandoValido() {
        // Arrange
        Livro livro = new Livro(null, "Clean Code", "Robert C. Martin", "9780132350884", 10);
        when(livroRepository.save(livro)).thenReturn(livro);

        // Act
        Livro livroCriado = livroService.criarLivro(livro);

        // Assert
        assertNotNull(livroCriado);
        verify(livroRepository, times(1)).save(livro);
    }
}

