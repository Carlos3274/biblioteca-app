package com.zecacompany.biblioteca.service;

import com.zecacompany.biblioteca.domain.Emprestimo;
import com.zecacompany.biblioteca.domain.Livro;
import com.zecacompany.biblioteca.domain.Usuario;
import com.zecacompany.biblioteca.repository.EmprestimoRepository;
import com.zecacompany.biblioteca.repository.LivroRepository;
import com.zecacompany.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Calendar;
import java.util.Date;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             UsuarioRepository usuarioRepository,
                             LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    public Emprestimo criarEmprestimo(Long usuarioId, Long livroId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));

        if (emprestimoRepository.countByUsuarioId(usuarioId) >= 2) {
            throw new IllegalArgumentException("O usuário já possui 2 livros emprestados.");
        }

        if (livro.getQuantidadeDisponivel() <= 0) {
            throw new IllegalArgumentException("Livro indisponível.");
        }

        Emprestimo emprestimo = Emprestimo.builder()
                .usuario(usuario)
                .livro(livro)
                .dataEmprestimo(new Date())
                .dataDevolucao(calcularDataDevolucao())
                .build();

        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> listarEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();
        return emprestimos;
    }

    public void devolverLivro(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado."));

        Livro livro = emprestimo.getLivro();
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
        livroRepository.save(livro);

        emprestimoRepository.delete(emprestimo);
    }


    private Date calcularDataDevolucao() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        return calendar.getTime();
    }


}
