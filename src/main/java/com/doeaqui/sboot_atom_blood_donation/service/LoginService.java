package com.doeaqui.sboot_atom_blood_donation.service;

import com.doeaqui.sboot_atom_blood_donation.model.NewLoginRequest;

public interface LoginService {

    void postNewLogin(NewLoginRequest loginRequest, Integer idUsuario);
}