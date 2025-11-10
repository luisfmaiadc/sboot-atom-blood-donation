INSERT INTO TbDoacao (
    idUsuario,
    idSolicitacaoDoacao,
    idHemocentro,
    dataDoacao,
    volume,
    observacoes
) VALUES (
    :idUsuario,
    :idSolicitacaoDoacao,
    :idHemocentro,
    :dataDoacao,
    :volume,
    :observacoes
);