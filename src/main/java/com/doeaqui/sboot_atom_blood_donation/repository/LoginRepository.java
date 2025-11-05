package com.doeaqui.sboot_atom_blood_donation.repository;

import com.doeaqui.sboot_atom_blood_donation.domain.Login;

import java.util.Optional;

public interface LoginRepository {

    void postNewLogin(Login login);
    Login findByEmail(String email);
    Optional<Login> getLoginInfoById(Integer idUsuario);
    void patchLoginEmailOrPapel(Login login);
    void patchLoginSenha(Login login);
}