package com.doeaqui.sboot_doe_aqui_monolith.mapper;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Login;
import com.doeaqui.sboot_doe_aqui_monolith.model.LoginResponse;
import com.doeaqui.sboot_doe_aqui_monolith.model.NewLoginRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    Login toLogin(NewLoginRequest newLoginRequest);
    LoginResponse toLoginResponse(Login login);
}