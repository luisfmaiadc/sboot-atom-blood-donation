package com.doeaqui.sboot_atom_blood_donation.mapper;

import com.doeaqui.sboot_atom_blood_donation.domain.Login;
import com.doeaqui.sboot_atom_blood_donation.model.LoginResponse;
import com.doeaqui.sboot_atom_blood_donation.model.NewLoginRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    Login toLogin(NewLoginRequest newLoginRequest);
    LoginResponse toLoginResponse(Login login);
}