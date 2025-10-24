-- Tabela de tipos sanguíneos
CREATE TABLE TbTipoSanguineo(
    id TINYINT UNSIGNED AUTO_INCREMENT,
    tipo VARCHAR(2) NOT NULL,
    fator CHAR(1) NOT NULL,
    PRIMARY KEY (id)
);

-- Tabela de papéis de usuário (ex: DOADOR, PACIENTE, ADMIN)
CREATE TABLE TbPapel(
    id TINYINT UNSIGNED AUTO_INCREMENT,
    nome VARCHAR(50) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

-- Tabela de usuários
CREATE TABLE TbUsuario (
    id INT AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    dataNascimento DATE NOT NULL,
    cpf CHAR(11) UNIQUE NOT NULL,
    genero CHAR(1) NOT NULL,
    telefone VARCHAR(20),
    idTipoSanguineo TINYINT UNSIGNED NOT NULL,
    idPapel TINYINT UNSIGNED NOT NULL,
    ativo TINYINT(1) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (idTipoSanguineo) REFERENCES TbTipoSanguineo(id),
    FOREIGN KEY (idPapel) REFERENCES TbPapel(id)
);

-- Tabela de logins de usuários
CREATE TABLE TbLogin (
    idUsuario INT,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    ultimoLogin DATETIME,
    tentativasFalhas TINYINT UNSIGNED,
    PRIMARY KEY (idUsuario),
    FOREIGN KEY (idUsuario) REFERENCES TbUsuario(id) ON DELETE CASCADE
);

-- Tabela de hemocentros
CREATE TABLE TbHemocentro(
    id INT AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    PRIMARY KEY (id)
);

-- Tabela de solicitações de doação
CREATE TABLE TbSolicitacaoDoacao (
    id INT AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    idHemocentro INT NOT NULL,
    idTipoSanguineo TINYINT UNSIGNED NOT NULL,
    dataSolicitacao DATETIME NOT NULL,
    status ENUM('ABERTA', 'EM_ANDAMENTO', 'ENCERRADA', 'CANCELADA') NOT NULL,
    dataEncerramento DATETIME,
    observacoes TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (idUsuario) REFERENCES TbUsuario(id),
    FOREIGN KEY (idHemocentro) REFERENCES TbHemocentro(id),
    FOREIGN KEY (idTipoSanguineo) REFERENCES TbTipoSanguineo(id)
);

-- Tabela de doações efetivas
CREATE TABLE TbDoacao (
    id INT AUTO_INCREMENT,
    idUsuario INT NOT NULL,
    idSolicitacaoDoacao INT,
    idHemocentro INT NOT NULL,
    dataDoacao DATETIME NOT NULL,
    volume INT,
    observacoes TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (idUsuario) REFERENCES TbUsuario(id),
    FOREIGN KEY (idSolicitacaoDoacao) REFERENCES TbSolicitacaoDoacao(id),
    FOREIGN KEY (idHemocentro) REFERENCES TbHemocentro(id)
);