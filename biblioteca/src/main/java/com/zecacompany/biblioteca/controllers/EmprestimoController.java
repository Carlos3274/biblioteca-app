package com.zecacompany.biblioteca.controllers;



import com.zecacompany.biblioteca.domain.Emprestimo;
import com.zecacompany.biblioteca.dto.EmprestimoDTO;
import com.zecacompany.biblioteca.service.EmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<EmprestimoDTO> criarEmprestimo(@RequestParam Long usuarioId, @RequestParam Long livroId) {
        Emprestimo emprestimoCriado = emprestimoService.criarEmprestimo(usuarioId, livroId);
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO(
                emprestimoCriado.getId(),
                emprestimoCriado.getUsuario().getId(),
                emprestimoCriado.getLivro().getId(),
                emprestimoCriado.getDataEmprestimo(),
                emprestimoCriado.getDataDevolucao());
        return ResponseEntity.ok(emprestimoDTO);
    }

    @PostMapping("/{id}/devolucao")
    public ResponseEntity<Void> devolverLivro(@PathVariable Long id) {
        emprestimoService.devolverLivro(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listarEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimos();
        List<EmprestimoDTO> emprestimoDTOs = emprestimos.stream()
                .map(emprestimo -> new EmprestimoDTO(emprestimo.getId(),
                        emprestimo.getUsuario().getId(),
                        emprestimo.getLivro().getId(),
                        emprestimo.getDataEmprestimo(),
                        emprestimo.getDataDevolucao()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(emprestimoDTOs);
    }
}
