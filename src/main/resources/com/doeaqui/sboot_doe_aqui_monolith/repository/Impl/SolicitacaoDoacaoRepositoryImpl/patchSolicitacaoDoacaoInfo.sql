UPDATE TbSolicitacaoDoacao
SET status = :status,
    observacoes = :observacoes,
    dataEncerramento = :dataEncerramento
WHERE id = :id;