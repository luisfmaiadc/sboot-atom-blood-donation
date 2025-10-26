SELECT tl.email, tl.senha
FROM TbLogin tl
WHERE tl.email = :email;