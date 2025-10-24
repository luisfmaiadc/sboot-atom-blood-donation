package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.Usuario;
import com.doeaqui.sboot_atom_blood_donation.mapper.UsuarioMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewUsuarioRequest;
import com.doeaqui.sboot_atom_blood_donation.repository.UsuarioRepository;
import com.doeaqui.sboot_atom_blood_donation.service.LoginService;
import com.doeaqui.sboot_atom_blood_donation.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final LoginService loginService;

    @Override
    @Transactional
    public Usuario postNewUser(NewUsuarioRequest usuarioRequest) {
        Usuario newUser = usuarioMapper.toUsuario(usuarioRequest);
        newUser.setAtivo(Boolean.TRUE);
        int generatedId = usuarioRepository.postNewUser(newUser);
        newUser.setId(generatedId);
        loginService.postNewLogin(usuarioRequest.getLogin(), generatedId);
        return newUser;
    }
}