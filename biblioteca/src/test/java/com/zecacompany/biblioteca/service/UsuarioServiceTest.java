package com.zecacompany.biblioteca.service;

import com.zecacompany.biblioteca.domain.Usuario;
import com.zecacompany.biblioteca.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EmailValidationService emailValidationService;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarUsuario_DeveSalvarUsuarioQuandoValido() {

        Usuario usuario = new Usuario(null, "João Silva", "joao.silva@example.com", new Date(90, Calendar.MAY, 15));
        when(emailValidationService.validarEmail(usuario.getEmail())).thenReturn(true);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario usuarioCriado = usuarioService.criarUsuario(usuario);

        assertNotNull(usuarioCriado);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void criarUsuario_DeveLancarExcecaoQuandoEmailInvalido() {

        Usuario usuario = new Usuario(null, "João Silva", "email_invalido", new Date(90, Calendar.MAY, 15));
        when(emailValidationService.validarEmail(usuario.getEmail())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.criarUsuario(usuario));
        assertEquals("O e-mail fornecido é inválido.", exception.getMessage());
    }

    @Test
    void criarUsuario_DeveLancarExcecaoQuandoMenorDeIdade() {

        Usuario usuario = new Usuario(null, "João Silva", "joao.silva@example.com", new Date(120, Calendar.MAY, 15)); // 3 anos de idade
        when(emailValidationService.validarEmail(usuario.getEmail())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.criarUsuario(usuario));
        assertEquals("O usuário deve ser maior de 18 anos.", exception.getMessage());
    }
}

