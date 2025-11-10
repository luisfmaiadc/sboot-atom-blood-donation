SELECT *
FROM TbHemocentro th
WHERE (:nome IS NULL OR th.nome LIKE :nome)
    AND (:telefone IS NULL OR th.telefone = :telefone)
    AND (:email IS NULL OR th.email = :email)
    AND th.ativo = true;