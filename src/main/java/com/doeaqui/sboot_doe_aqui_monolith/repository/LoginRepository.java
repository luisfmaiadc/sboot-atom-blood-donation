package com.doeaqui.sboot_doe_aqui_monolith.repository;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Login;

import java.util.Optional;

public interface LoginRepository {

    void postNewLogin(Login login);
    Login findByEmail(String email);
    Optional<Login> getLoginInfoById(Integer idUsuario);
    void patchLoginEmailOrPapel(Login login);
    void patchLoginSenha(Login login);
}