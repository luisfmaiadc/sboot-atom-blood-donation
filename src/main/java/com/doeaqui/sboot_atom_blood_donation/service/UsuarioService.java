package com.doeaqui.sboot_atom_blood_donation.service;

import com.doeaqui.sboot_atom_blood_donation.domain.Usuario;
import com.doeaqui.sboot_atom_blood_donation.model.NewUsuarioRequest;

public interface UsuarioService {

    Usuario postNewUser(NewUsuarioRequest usuarioRequest);
}