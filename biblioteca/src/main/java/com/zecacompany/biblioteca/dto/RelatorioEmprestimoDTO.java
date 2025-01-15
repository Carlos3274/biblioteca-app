package com.zecacompany.biblioteca.dto;

import java.util.Date;

public record RelatorioEmprestimoDTO(
        String nomeUsuario,
        String tituloLivro,
        Date dataEmprestimo,
        Date dataDevolucao
) {

}
