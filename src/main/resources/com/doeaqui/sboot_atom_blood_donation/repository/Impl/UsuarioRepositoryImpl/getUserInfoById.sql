SELECT tu.*, tl.email
FROM TbUsuario tu
INNER JOIN TbLogin tl ON tu.id = tl.idUsuario
WHERE tu.id = :id;