package com.doeaqui.sboot_atom_blood_donation.service;

import com.doeaqui.sboot_atom_blood_donation.model.NewUsuarioRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UpdateUsuarioRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UsuarioResponse;

public interface UsuarioService {

    UsuarioResponse postNewUser(NewUsuarioRequest usuarioRequest);
    UsuarioResponse getUserInfoById(Integer id);
    UsuarioResponse patchUserInfo(Integer idUsuario, UpdateUsuarioRequest updateUsuarioRequest);
    void deleteUser(Integer idUsuario);
}