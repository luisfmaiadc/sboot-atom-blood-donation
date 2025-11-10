SELECT tl.idUsuario,
        tl.email,
        tl.idPapel
FROM TbLogin tl
WHERE tl.idUsuario = :idUsuario;