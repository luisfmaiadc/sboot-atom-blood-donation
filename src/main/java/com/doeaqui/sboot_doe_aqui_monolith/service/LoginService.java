package com.doeaqui.sboot_doe_aqui_monolith.service;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Login;
import com.doeaqui.sboot_doe_aqui_monolith.model.NewLoginRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UpdateUsuarioRequest;

public interface LoginService {

    void postNewLogin(NewLoginRequest loginRequest, Integer idUsuario);
    Login getLoginInfoById(Integer idUsuario);
    void patchLoginInfo(Integer idUsuario, UpdateUsuarioRequest updateUsuarioRequest);
}