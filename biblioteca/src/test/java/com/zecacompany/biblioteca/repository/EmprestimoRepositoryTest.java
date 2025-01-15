package com.zecacompany.biblioteca.repository;

import com.zecacompany.biblioteca.domain.Emprestimo;
import com.zecacompany.biblioteca.domain.Livro;
import com.zecacompany.biblioteca.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmprestimoRepositoryTest {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Test
    void salvarEmprestimo_DevePersistirEmprestimo() {
        // Arrange
        Usuario usuario = usuarioRepository.save(
                new Usuario(
                        null,
                        "João Silva",
                        "joao.silva@example.com",
                        new Date()
                )
        );
        Livro livro = livroRepository.save(
                new Livro(
                        null,
                        "Clean Code",
                        "Robert C. Martin",
                        "9780132350884",
                        10));

        Emprestimo emprestimo = new Emprestimo(null, usuario, livro, new Date(), null);

        Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);

        assertNotNull(emprestimoSalvo.getId());
        assertEquals("João Silva", emprestimoSalvo.getUsuario().getNome());
        assertEquals("Clean Code", emprestimoSalvo.getLivro().getTitulo());
    }

    @Test
    void buscarEmprestimoPorId_DeveRetornarEmprestimo() {
        // Arrange
        Usuario usuario = usuarioRepository.save(
                new Usuario(
                        null,
                        "João Silva",
                        "joao.silva@example.com",
                        new Date()));

        Livro livro = livroRepository.save(
                new Livro(null,
                        "Clean Code",
                        "Robert C. Martin",
                        "9780132350884",
                        10));

        Emprestimo emprestimo = emprestimoRepository.save(
                new Emprestimo(null, usuario, livro, new Date(), null));

        // Act
        Optional<Emprestimo> emprestimoEncontrado = emprestimoRepository.findById(emprestimo.getId());

        // Assert
        assertTrue(emprestimoEncontrado.isPresent());
        assertEquals("João Silva", emprestimoEncontrado.get().getUsuario().getNome());
        assertEquals("Clean Code", emprestimoEncontrado.get().getLivro().getTitulo());
    }
}

