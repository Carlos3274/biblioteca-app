CREATE TABLE livro (
       id SERIAL PRIMARY KEY,
       titulo VARCHAR(255) NOT NULL,
       autor VARCHAR(255) NOT NULL,
       isbn VARCHAR(13) NOT NULL UNIQUE,
       quantidade_disponivel INTEGER NOT NULL
);
