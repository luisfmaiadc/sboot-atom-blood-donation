UPDATE TbLogin
SET email = :email,
    idPapel = :idPapel
WHERE idUsuario = :idUsuario;