package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.config.exception.ResourceNotFoundException;
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
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

        boolean userFieldsChanged = applyUserUpdates(updateRequest, userInfo);
        boolean loginFieldsChanged = isUpdateLoginValid(updateRequest, usuario);

        if (!userFieldsChanged && !loginFieldsChanged)
            throw new IllegalArgumentException("Informe ao menos um campo para atualizar ou os valores informados são os mesmos dos atuais.");

        if (userFieldsChanged) {
            usuarioRepository.patchUserInfo(userInfo);
        }

        if (loginFieldsChanged) {
            loginService.patchLoginInfo(idUsuario, updateRequest);
        }

        return getUserInfoById(idUsuario);
    }

    @Override
    @Transactional
    public void deleteUser(Integer idUsuario) {
        UsuarioResponse usuario = getUserInfoById(idUsuario);
        if (Objects.equals(usuario.getAtivo(), Boolean.FALSE)) throw new IllegalArgumentException("Usuário já desativado.");
        usuarioRepository.deleteUser(idUsuario);
    }

    private boolean applyUserUpdates(UpdateUsuarioRequest request, Usuario usuarioToUpdate) {
        boolean hasChanges = false;
        final Set<String> supportedGendersSet = Set.of("M", "F", "O");

        if (request.getGenero() != null && !Objects.equals(usuarioToUpdate.getGenero(), request.getGenero().charAt(0))) {
            if (!supportedGendersSet.contains(request.getGenero())) throw new IllegalArgumentException("Gênero informado não suportado.");
            usuarioToUpdate.setGenero(request.getGenero().charAt(0));
            hasChanges = true;
        }

        if (request.getTelefone() != null && !Objects.equals(usuarioToUpdate.getTelefone(), request.getTelefone())) {
            usuarioToUpdate.setTelefone(request.getTelefone());
            hasChanges = true;
        }

        if (request.getIdPapel() != null && !Objects.equals(usuarioToUpdate.getIdPapel(), request.getIdPapel().byteValue())) {
            var papelList = papelService.getPapeisUsuarios();
            if (papelList.stream().noneMatch(papel -> papel.getId().equals(request.getIdPapel().byteValue()))) {
                throw new IllegalArgumentException("Papel informado inválido.");
            }
            usuarioToUpdate.setIdPapel(request.getIdPapel().byteValue());
            hasChanges = true;
        }

        return hasChanges;
    }

    private boolean isUpdateLoginValid(UpdateUsuarioRequest updateRequest, UsuarioResponse usuario) {
        boolean isUpdateLoginValid = false;

        if (updateRequest.getEmail() != null && !Objects.equals(updateRequest.getEmail(), usuario.getEmail())) {
            isUpdateLoginValid = true;
        }

        if (updateRequest.getSenha() != null) {
            isUpdateLoginValid = true;
        }

        return isUpdateLoginValid;
    }
}