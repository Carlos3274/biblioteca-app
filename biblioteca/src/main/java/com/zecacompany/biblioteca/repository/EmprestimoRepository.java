package com.zecacompany.biblioteca.repository;

import com.zecacompany.biblioteca.domain.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    Integer countByUsuarioId(Long usuarioId);
}
