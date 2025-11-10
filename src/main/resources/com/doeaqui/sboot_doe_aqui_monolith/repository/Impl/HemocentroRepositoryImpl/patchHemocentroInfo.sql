UPDATE TbHemocentro
SET telefone = :telefone,
    email = :email,
    ativo = :ativo
WHERE id = :id;