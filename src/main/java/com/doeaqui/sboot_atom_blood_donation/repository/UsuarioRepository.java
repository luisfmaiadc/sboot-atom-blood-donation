package com.doeaqui.sboot_atom_blood_donation.repository;

import com.doeaqui.sboot_atom_blood_donation.domain.Usuario;
import com.doeaqui.sboot_atom_blood_donation.model.UsuarioResponse;

import java.util.Optional;

public interface UsuarioRepository {

    int postNewUser(Usuario usuario);
    Optional<UsuarioResponse> getUserInfoById(Integer id);
    void patchUserInfo(Usuario usuario);
}