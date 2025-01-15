package com.zecacompany.biblioteca.dto;

import java.util.Date;

public record UsuarioDTO(Long id, String nome, String email, Date dataNascimento) {
}
