SELECT *
FROM TbDoacao td
WHERE (:idHemocentro IS NULL OR td.idHemocentro = :idHemocentro)
    AND (:dataDoacao IS NULL OR CAST(td.dataDoacao AS DATE) = :dataDoacao)
    AND (:volume IS NULL OR td.volume = :volume)
    AND td.idUsuario = :idUsuario;