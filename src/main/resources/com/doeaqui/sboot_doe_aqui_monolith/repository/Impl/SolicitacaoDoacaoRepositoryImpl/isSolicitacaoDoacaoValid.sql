SELECT EXISTS (
    SELECT 1
    FROM TbSolicitacaoDoacao tsd
    WHERE tsd.idUsuario = :idUsuario
        AND tsd.status IN ('ABERTA', 'EM_ANDAMENTO')
);