package com.zecacompany.biblioteca.repository;

import com.zecacompany.biblioteca.domain.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;

    @Test
    void salvarLivro_DevePersistirLivro() {
        // Arrange
        Livro livro = new Livro(null, "Clean Code", "Robert C. Martin", "9780132350884", 10);

        // Act
        Livro livroSalvo = livroRepository.save(livro);

        // Assert
        assertNotNull(livroSalvo.getId());
        assertEquals("Clean Code", livroSalvo.getTitulo());
    }

    @Test
    void buscarLivroPorId_DeveRetornarLivro() {
        // Arrange
        Livro livro = new Livro(null, "Clean Code", "Robert C. Martin", "9780132350884", 10);
        Livro livroSalvo = livroRepository.save(livro);

        // Act
        Optional<Livro> livroEncontrado = livroRepository.findById(livroSalvo.getId());

        // Assert
        assertTrue(livroEncontrado.isPresent());
        assertEquals("Clean Code", livroEncontrado.get().getTitulo());
    }
}

