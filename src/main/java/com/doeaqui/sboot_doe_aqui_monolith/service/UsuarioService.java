package com.doeaqui.sboot_doe_aqui_monolith.service;

import com.doeaqui.sboot_doe_aqui_monolith.model.NewUsuarioRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UpdateUsuarioRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UsuarioResponse;

public interface UsuarioService {

    UsuarioResponse postNewUser(NewUsuarioRequest usuarioRequest);
    UsuarioResponse getUserInfoById(Integer id);
    UsuarioResponse patchUserInfo(Integer idUsuario, UpdateUsuarioRequest updateUsuarioRequest);
    void deleteUser(Integer idUsuario);
}