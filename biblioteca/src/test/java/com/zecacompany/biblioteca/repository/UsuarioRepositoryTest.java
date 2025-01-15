package com.zecacompany.biblioteca.repository;

import com.zecacompany.biblioteca.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void salvarUsuario_DevePersistirUsuario() {
        // Arrange
        Usuario usuario = new Usuario(null, "João Silva", "joao.silva@example.com", new java.util.Date());

        // Act
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Assert
        assertNotNull(usuarioSalvo.getId());
        assertEquals("João Silva", usuarioSalvo.getNome());
    }

    @Test
    void buscarUsuarioPorId_DeveRetornarUsuario() {
        // Arrange
        Usuario usuario = new Usuario(null, "João Silva", "joao.silva@example.com", new java.util.Date());
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Act
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(usuarioSalvo.getId());

        // Assert
        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("João Silva", usuarioEncontrado.get().getNome());
    }
}

