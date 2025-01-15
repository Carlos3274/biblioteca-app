package com.zecacompany.biblioteca.dto;

import java.util.Date;

public record EmprestimoDTO(Long id, Long usuarioId, Long livroId, Date dataEmprestimo, Date dataDevolucao) {
}
