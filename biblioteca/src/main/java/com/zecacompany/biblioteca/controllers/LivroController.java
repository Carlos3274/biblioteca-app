package com.zecacompany.biblioteca.controllers;



import com.zecacompany.biblioteca.domain.Livro;
import com.zecacompany.biblioteca.dto.LivroDTO;
import com.zecacompany.biblioteca.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<LivroDTO> criarLivro(@RequestBody Livro livro) {
        Livro livroCriado = livroService.criarLivro(livro);
        LivroDTO livroDTO = new LivroDTO(
                livroCriado.getId(),
                livroCriado.getTitulo(),
                livroCriado.getAutor(),
                livroCriado.getIsbn(),
                livroCriado.getQuantidadeDisponivel());
        return ResponseEntity.ok(livroDTO);
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarLivros() {
        List<Livro> livros = livroService.listarLivros();
        List<LivroDTO> livroDTOs = livros.stream()
                .map(livro -> new LivroDTO(
                        livro.getId(),
                        livro.getTitulo(),
                        livro.getAutor(),
                        livro.getIsbn(),
                        livro.getQuantidadeDisponivel()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(livroDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarLivroPorId(@PathVariable Long id) {
        Optional<Livro> livro = livroService.buscarLivroPorId(id);
        return livro.map(l -> new LivroDTO(
                        l.getId(),
                        l.getTitulo(),
                        l.getAutor(),
                        l.getIsbn(),
                        l.getQuantidadeDisponivel()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

