package com.zecacompany.biblioteca.service;

import com.zecacompany.biblioteca.domain.Livro;
import com.zecacompany.biblioteca.domain.Usuario;
import com.zecacompany.biblioteca.repository.EmprestimoRepository;
import com.zecacompany.biblioteca.repository.LivroRepository;
import com.zecacompany.biblioteca.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarEmprestimo_DeveLancarExcecaoQuandoUsuarioJaPossuiDoisEmprestimos() {
        // Arrange
        Long usuarioId = 1L;
        Long livroId = 1L;

        Usuario usuario = new Usuario(usuarioId, "João Silva", "joao.silva@example.com", new Date());
        Livro livro = new Livro(livroId, "Clean Code", "Robert C. Martin", "9780132350884", 10);

        when(livroRepository.findById(livroId)).thenReturn(Optional.of(livro));

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(emprestimoRepository.countByUsuarioId(usuarioId)).thenReturn(2);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> emprestimoService.criarEmprestimo(usuarioId, livroId));
        assertEquals("O usuário já possui 2 livros emprestados.", exception.getMessage());
    }

}