package com.doeaqui.sboot_atom_blood_donation.service;

import com.doeaqui.sboot_atom_blood_donation.domain.Login;
import com.doeaqui.sboot_atom_blood_donation.model.NewLoginRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UpdateUsuarioRequest;

public interface LoginService {

    void postNewLogin(NewLoginRequest loginRequest, Integer idUsuario);
    Login getLoginInfoById(Integer idUsuario);
    void patchLoginInfo(Integer idUsuario, UpdateUsuarioRequest updateUsuarioRequest);
}