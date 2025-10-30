package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.config.exception.ResourceNotFoundException;
import com.doeaqui.sboot_atom_blood_donation.domain.Login;
import com.doeaqui.sboot_atom_blood_donation.mapper.LoginMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewLoginRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UpdateUsuarioRequest;
import com.doeaqui.sboot_atom_blood_donation.repository.LoginRepository;
import com.doeaqui.sboot_atom_blood_donation.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Override
    public Login getLoginInfoById(Integer idUsuario) {
        Optional<Login> optionalLogin = repository.getLoginInfoById(idUsuario);
        if (optionalLogin.isEmpty()) throw new ResourceNotFoundException("Nenhuma informação de login foi encontrada.");
        return optionalLogin.get();
    }

    @Override
    @Transactional
    public Login patchLoginInfo(Integer idUsuario, UpdateUsuarioRequest updateRequest) {
        Login login = getLoginInfoById(idUsuario);

        if (updateRequest.getEmail() != null && !login.getEmail().equals(updateRequest.getEmail())) {
            login.setEmail(updateRequest.getEmail());
            repository.patchLoginEmail(login);
        }

        if (updateRequest.getSenha() != null) {
            String newSenha = passwordEncoder.encode(updateRequest.getSenha());
            login.setSenha(newSenha);
            repository.patchLoginSenha(login);
        }

        return login;
    }
}