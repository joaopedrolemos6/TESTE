CREATE DATABASE teste;

USE teste;

SELECT DATABASE ();

CREATE TABLE Pessoas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    tipo ENUM('Aluno', 'Funcionario') NOT NULL
);

CREATE TABLE Alunos (
    id INT PRIMARY KEY,
    notaProva1 DECIMAL(5, 2),
    notaProva2 DECIMAL(5, 2),
    FOREIGN KEY (id) REFERENCES Pessoas(id)
);

CREATE TABLE Funcionarios (
    id INT PRIMARY KEY,
    cargo VARCHAR(100),
    FOREIGN KEY (id) REFERENCES Pessoas(id)
);

CREATE TABLE Livros (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL
);

CREATE TABLE Emprestimos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pessoa_id INT,
    livro_id INT,
    FOREIGN KEY (pessoa_id) REFERENCES Pessoas(id),
    FOREIGN KEY (livro_id) REFERENCES Livros(id)
);

CREATE TABLE Pedidos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pessoa_id INT,
    valor DECIMAL(10, 2),
    FOREIGN KEY (pessoa_id) REFERENCES Pessoas(id)
);
