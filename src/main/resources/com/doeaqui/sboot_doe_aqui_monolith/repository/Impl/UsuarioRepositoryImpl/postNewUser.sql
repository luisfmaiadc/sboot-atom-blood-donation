INSERT INTO TbUsuario (
    nome,
    dataNascimento,
    cpf,
    genero,
    telefone,
    idTipoSanguineo,
    ativo
) VALUES (
    :nome,
    :dataNascimento,
    :cpf,
    :genero,
    :telefone,
    :idTipoSanguineo,
    :ativo
);