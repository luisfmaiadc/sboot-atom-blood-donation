package com.doeaqui.sboot_atom_blood_donation.repository;

import com.doeaqui.sboot_atom_blood_donation.domain.Login;

public interface LoginRepository {

    void postNewLogin(Login login);
}