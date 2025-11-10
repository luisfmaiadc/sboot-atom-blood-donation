SELECT *
FROM TbDoacao td
WHERE td.idUsuario = :idUsuario
ORDER BY td.dataDoacao DESC
LIMIT 1;