package com.doeaqui.sboot_atom_blood_donation.controller;

import com.doeaqui.sboot_atom_blood_donation.api.LoginApiDelegate;
import com.doeaqui.sboot_atom_blood_donation.config.security.TokenService;
import com.doeaqui.sboot_atom_blood_donation.domain.Login;
import com.doeaqui.sboot_atom_blood_donation.model.LoginRequest;
import com.doeaqui.sboot_atom_blood_donation.model.LoginResponse;
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

    @Override
    public ResponseEntity<LoginResponse> postLoginCredentials(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        LoginResponse response = tokenService.getToken((Login) authentication.getPrincipal());
        return ResponseEntity.ok(response);
    }
}