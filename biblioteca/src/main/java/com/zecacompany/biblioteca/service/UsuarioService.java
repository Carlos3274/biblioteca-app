package com.zecacompany.biblioteca.service;


import com.zecacompany.biblioteca.domain.Usuario;

import com.zecacompany.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmailValidationService emailValidationService;

    public UsuarioService(UsuarioRepository usuarioRepository, EmailValidationService emailValidationService) {
        this.usuarioRepository = usuarioRepository;
        this.emailValidationService = emailValidationService;
    }

    public Usuario criarUsuario(Usuario usuario) {
        if (usuario.getDataNascimento() == null || !isMaiorDeIdade(usuario.getDataNascimento())) {
            throw new IllegalArgumentException("O usuário deve ser maior de 18 anos.");
        }

        if (!emailValidationService.validarEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("O e-mail fornecido é inválido.");
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    private boolean isMaiorDeIdade(Date dataNascimento) {
        Calendar hoje = Calendar.getInstance();
        Calendar nascimento = Calendar.getInstance();
        nascimento.setTime(dataNascimento);

        int idade = hoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);
        if (hoje.get(Calendar.DAY_OF_YEAR) < nascimento.get(Calendar.DAY_OF_YEAR)) {
            idade--;
        }
        return idade >= 18;
    }


}