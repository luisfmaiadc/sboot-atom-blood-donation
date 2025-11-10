package com.doeaqui.sboot_doe_aqui_monolith.controller;

import com.doeaqui.sboot_doe_aqui_monolith.api.LoginApiDelegate;
import com.doeaqui.sboot_doe_aqui_monolith.config.security.CustomUserDetails;
import com.doeaqui.sboot_doe_aqui_monolith.config.security.TokenService;
import com.doeaqui.sboot_doe_aqui_monolith.domain.Login;
import com.doeaqui.sboot_doe_aqui_monolith.mapper.LoginMapper;
import com.doeaqui.sboot_doe_aqui_monolith.model.AuthenticationResponse;
import com.doeaqui.sboot_doe_aqui_monolith.model.LoginRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.LoginResponse;
import com.doeaqui.sboot_doe_aqui_monolith.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.idUsuario == #idUsuario")
    public ResponseEntity<LoginResponse> getLoginInfoById(Integer idUsuario) {
        Login login = loginService.getLoginInfoById(idUsuario);
        LoginResponse response = mapper.toLoginResponse(login);
        return ResponseEntity.ok(response);
    }
}