package com.doeaqui.sboot_atom_blood_donation.repository;

import com.doeaqui.sboot_atom_blood_donation.domain.Usuario;

public interface UsuarioRepository {

    int postNewUser(Usuario usuario);
}