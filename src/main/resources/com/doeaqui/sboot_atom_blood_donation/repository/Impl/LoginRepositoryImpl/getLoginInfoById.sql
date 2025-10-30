SELECT tl.idUsuario,
        tl.email
FROM TbLogin tl
WHERE tl.idUsuario = :idUsuario;