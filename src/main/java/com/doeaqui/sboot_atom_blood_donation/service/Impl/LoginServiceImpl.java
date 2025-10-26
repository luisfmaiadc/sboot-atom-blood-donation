package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.Login;
import com.doeaqui.sboot_atom_blood_donation.mapper.LoginMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewLoginRequest;
import com.doeaqui.sboot_atom_blood_donation.repository.LoginRepository;
import com.doeaqui.sboot_atom_blood_donation.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginRepository repository;
    private final LoginMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void postNewLogin(NewLoginRequest loginRequest, Integer idUsuario) {
        Login newLogin = mapper.toLogin(loginRequest);
        String senha = passwordEncoder.encode(newLogin.getSenha());
        newLogin.setIdUsuario(idUsuario);
        newLogin.setSenha(senha);
        repository.postNewLogin(newLogin);
    }
}