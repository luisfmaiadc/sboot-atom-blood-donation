package com.doeaqui.sboot_atom_blood_donation.controller;

import com.doeaqui.sboot_atom_blood_donation.api.LoginApiDelegate;
import com.doeaqui.sboot_atom_blood_donation.config.security.CustomUserDetails;
import com.doeaqui.sboot_atom_blood_donation.config.security.TokenService;
import com.doeaqui.sboot_atom_blood_donation.domain.Login;
import com.doeaqui.sboot_atom_blood_donation.mapper.LoginMapper;
import com.doeaqui.sboot_atom_blood_donation.model.AuthenticationResponse;
import com.doeaqui.sboot_atom_blood_donation.model.LoginRequest;
import com.doeaqui.sboot_atom_blood_donation.model.LoginResponse;
import com.doeaqui.sboot_atom_blood_donation.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginApiImpl implements LoginApiDelegate {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final LoginService loginService;
    private final LoginMapper mapper;

    @Override
    public ResponseEntity<AuthenticationResponse> postLoginCredentials(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        AuthenticationResponse response = tokenService.getToken((CustomUserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LoginResponse> getLoginInfoById(Integer idUsuario) {
        Login login = loginService.getLoginInfoById(idUsuario);
        LoginResponse response = mapper.toLoginResponse(login);
        return ResponseEntity.ok(response);
    }
}