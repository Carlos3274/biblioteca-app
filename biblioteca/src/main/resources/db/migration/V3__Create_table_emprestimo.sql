CREATE TABLE emprestimo (
        id SERIAL PRIMARY KEY,
        usuario_id BIGINT NOT NULL,
        livro_id BIGINT NOT NULL,
        data_emprestimo DATE NOT NULL,
        data_devolucao DATE,
        CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE,
        CONSTRAINT fk_livro FOREIGN KEY (livro_id) REFERENCES livro (id) ON DELETE CASCADE
);
