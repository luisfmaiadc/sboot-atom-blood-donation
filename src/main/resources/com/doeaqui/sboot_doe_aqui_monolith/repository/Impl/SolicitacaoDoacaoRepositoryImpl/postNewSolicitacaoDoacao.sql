INSERT INTO TbSolicitacaoDoacao (
    idUsuario,
    idHemocentro,
    idTipoSanguineo,
    dataSolicitacao,
    status,
    observacoes
) VALUES (
    :idUsuario,
    :idHemocentro,
    :idTipoSanguineo,
    :dataSolicitacao,
    :status,
    :observacoes
);