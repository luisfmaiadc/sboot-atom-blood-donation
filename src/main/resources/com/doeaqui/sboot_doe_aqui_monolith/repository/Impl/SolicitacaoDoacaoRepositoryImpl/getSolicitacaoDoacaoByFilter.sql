SELECT tsd.*
FROM TbSolicitacaoDoacao tsd
WHERE (:idUsuario IS NULL OR tsd.idUsuario = :idUsuario)
  AND (:idHemocentro IS NULL OR tsd.idHemocentro = :idHemocentro)
  AND (:idTipoSanguineo IS NULL OR tsd.idTipoSanguineo = :idTipoSanguineo)
  AND (:dataSolicitacao IS NULL OR CAST(tsd.dataSolicitacao AS DATE) = :dataSolicitacao)
  AND (:status IS NULL OR tsd.status = :status)
  AND (:dataEncerramento IS NULL OR CAST(tsd.dataEncerramento AS DATE) = :dataEncerramento);