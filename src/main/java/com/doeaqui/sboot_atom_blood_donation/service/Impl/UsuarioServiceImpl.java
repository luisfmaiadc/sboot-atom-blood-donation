package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.config.exception.ResourceNotFoundException;
import com.doeaqui.sboot_atom_blood_donation.domain.Login;
import com.doeaqui.sboot_atom_blood_donation.domain.Usuario;
import com.doeaqui.sboot_atom_blood_donation.mapper.UsuarioMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewUsuarioRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UpdateUsuarioRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UsuarioResponse;
import com.doeaqui.sboot_atom_blood_donation.repository.UsuarioRepository;
import com.doeaqui.sboot_atom_blood_donation.service.LoginService;
import com.doeaqui.sboot_atom_blood_donation.service.PapelService;
import com.doeaqui.sboot_atom_blood_donation.service.UsuarioService;
import com.doeaqui.sboot_atom_blood_donation.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final LoginService loginService;
    private final PapelService papelService;

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

    @Override
    public UsuarioResponse getUserInfoById(Integer id) {
        Optional<UsuarioResponse> optionalUsuario = usuarioRepository.getUserInfoById(id);
        if (optionalUsuario.isEmpty()) throw new ResourceNotFoundException("Nenhuma informação do usuário foi encontrada.");
        return optionalUsuario.get();
    }

    @Override
    @Transactional
    public UsuarioResponse patchUserInfo(Integer idUsuario, UpdateUsuarioRequest updateRequest) {
        AppUtils.requireAtLeastOneNonNull(Arrays.asList(updateRequest.getEmail(), updateRequest.getSenha(), updateRequest.getGenero(),
                updateRequest.getTelefone(), updateRequest.getIdPapel()));

        UsuarioResponse usuario = getUserInfoById(idUsuario);
        Usuario userInfo = usuarioMapper.toUsuarioFromResponse(usuario);
        boolean isUpdateUserValid = isUpdateUserValid(updateRequest, usuario, userInfo);
        boolean isUpdateLoginValid = isUpdateLoginValid(updateRequest, usuario);

        if (!isUpdateUserValid && !isUpdateLoginValid) throw new IllegalArgumentException("Informe ao menos um campo para atualizar.");

        if (isUpdateUserValid) {
            usuarioRepository.patchUserInfo(userInfo);
        }

        if (isUpdateLoginValid) {
            Login updatedLogin = loginService.patchLoginInfo(idUsuario, updateRequest);
            usuario.setEmail(updatedLogin.getEmail());
        }

        return usuario;
    }

    private boolean isUpdateUserValid(UpdateUsuarioRequest updateRequest, UsuarioResponse usuario, Usuario userInfo) {
        boolean isUpdateValid = false;

        if (updateRequest.getGenero() != null && !usuario.getGenero().equals(updateRequest.getGenero())) {
            if (!updateRequest.getGenero().equals("M") && !updateRequest.getGenero().equals("F")
                    && !updateRequest.getGenero().equals("O")) throw new IllegalArgumentException("Gênero informado não suportado.");
            userInfo.setGenero(updateRequest.getGenero().charAt(0));
            usuario.setGenero(updateRequest.getGenero());
            isUpdateValid = true;
        }

        if (updateRequest.getTelefone() != null && !usuario.getTelefone().equals(updateRequest.getTelefone())) {
            userInfo.setTelefone(updateRequest.getTelefone());
            usuario.setTelefone(updateRequest.getTelefone());
            isUpdateValid = true;
        }

        if (updateRequest.getIdPapel() != null && !usuario.getIdPapel().equals(updateRequest.getIdPapel())) {
            var papelList = papelService.getPapeisUsuarios();
            if (papelList.stream().noneMatch(papel -> papel.getId().equals(updateRequest.getIdPapel().byteValue())))
                throw new IllegalArgumentException("Papel informado inválido.");
            userInfo.setIdPapel(updateRequest.getIdPapel().byteValue());
            usuario.setIdPapel(updateRequest.getIdPapel());
            isUpdateValid = true;
        }

        return isUpdateValid;
    }

    private boolean isUpdateLoginValid(UpdateUsuarioRequest updateRequest, UsuarioResponse usuario) {
        boolean isUpdateLoginValid = false;

        if (updateRequest.getEmail() != null && !usuario.getEmail().equals(updateRequest.getEmail())) {
            isUpdateLoginValid = true;
        }

        if (updateRequest.getSenha() != null) {
            isUpdateLoginValid = true;
        }

        return isUpdateLoginValid;
    }
}