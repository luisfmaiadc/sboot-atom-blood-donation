INSERT INTO TbUsuario (
    nome,
    dataNascimento,
    cpf,
    genero,
    telefone,
    idTipoSanguineo,
    idPapel,
    ativo
) VALUES (
    :nome,
    :dataNascimento,
    :cpf,
    :genero,
    :telefone,
    :idTipoSanguineo,
    :idPapel,
    :ativo
);