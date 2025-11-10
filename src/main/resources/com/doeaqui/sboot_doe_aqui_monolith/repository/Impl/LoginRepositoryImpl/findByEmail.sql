SELECT tl.idUsuario,
       tl.email,
       tl.senha,
       tl.idPapel,
       tl.tentativasFalhas
FROM TbLogin tl
WHERE tl.email = :email;