package com.doeaqui.sboot_doe_aqui_monolith.service.Impl;

import com.doeaqui.sboot_doe_aqui_monolith.config.exception.ResourceNotFoundException;
import com.doeaqui.sboot_doe_aqui_monolith.domain.Papel;
import com.doeaqui.sboot_doe_aqui_monolith.domain.TipoSanguineo;
import com.doeaqui.sboot_doe_aqui_monolith.domain.Usuario;
import com.doeaqui.sboot_doe_aqui_monolith.mapper.UsuarioMapper;
import com.doeaqui.sboot_doe_aqui_monolith.model.NewUsuarioRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UpdateUsuarioRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UsuarioResponse;
import com.doeaqui.sboot_doe_aqui_monolith.repository.UsuarioRepository;
import com.doeaqui.sboot_doe_aqui_monolith.service.LoginService;
import com.doeaqui.sboot_doe_aqui_monolith.service.PapelService;
import com.doeaqui.sboot_doe_aqui_monolith.service.TipoSanguineoService;
import com.doeaqui.sboot_doe_aqui_monolith.service.UsuarioService;
import com.doeaqui.sboot_doe_aqui_monolith.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final LoginService loginService;
    private final PapelService papelService;
    private final TipoSanguineoService tipoSanguineoService;

    @Override
    @Transactional
    public UsuarioResponse postNewUser(NewUsuarioRequest usuarioRequest) {
        validateNewUserRequest(usuarioRequest);
        Usuario newUser = usuarioMapper.toUsuario(usuarioRequest);
        newUser.setAtivo(Boolean.TRUE);
        int generatedId = usuarioRepository.postNewUser(newUser);
        newUser.setId(generatedId);
        loginService.postNewLogin(usuarioRequest.getLogin(), generatedId);
        return getUserInfoById(generatedId);
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

    private void validateNewUserRequest(NewUsuarioRequest usuarioRequest) {
        validadeGenders(usuarioRequest.getGenero());
        validateCpf(usuarioRequest.getCpf());
        validateTipoSanguineo(usuarioRequest.getIdTipoSanguineo());
        validatePapel(usuarioRequest.getLogin().getIdPapel());
    }

    private void validateTipoSanguineo(Integer idTipoSanguineo) {
        List<TipoSanguineo> tipoSanguineoList = tipoSanguineoService.getTiposSanguineos();
        if (tipoSanguineoList.stream().noneMatch(t -> t.getId() == idTipoSanguineo.byteValue()))
            throw new IllegalArgumentException("Tipo sanguíneo informado inválido.");
    }

    private void validatePapel(Integer idPapel) {
        List<Papel> papelList = papelService.getPapeisUsuarios();
        if (papelList.stream().noneMatch(papel -> papel.getId() == idPapel.byteValue())
                || idPapel.byteValue() == 1) throw new IllegalArgumentException("Papel informado inválido.");
    }

    private void validadeGenders(String gender) {
        final Set<String> supportedGendersSet = Set.of("M", "F", "O");
        if (!supportedGendersSet.contains(gender))
            throw new IllegalArgumentException("Gênero informado não suportado.");
    }

    private void validateCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}") || cpf.chars().distinct().count() == 1) {
            throw new IllegalArgumentException("CPF inválido.");
        }

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += (cpf.charAt(i) - '0') * (10 - i);
            }
            int firstVerifier = 11 - (sum % 11);
            if (firstVerifier >= 10) {
                firstVerifier = 0;
            }

            if ((cpf.charAt(9) - '0') != firstVerifier) {
                throw new IllegalArgumentException("CPF inválido.");
            }

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += (cpf.charAt(i) - '0') * (11 - i);
            }
            int secondVerifier = 11 - (sum % 11);
            if (secondVerifier >= 10) {
                secondVerifier = 0;
            }

            if ((cpf.charAt(10) - '0') != secondVerifier) {
                throw new IllegalArgumentException("CPF inválido.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("CPF deve conter apenas números.");
        }
    }

    private boolean applyUserUpdates(UpdateUsuarioRequest request, Usuario usuarioToUpdate) {
        boolean hasChanges = false;

        if (request.getGenero() != null && !Objects.equals(usuarioToUpdate.getGenero(), request.getGenero().charAt(0))) {
            validadeGenders(request.getGenero());
            usuarioToUpdate.setGenero(request.getGenero().charAt(0));
            hasChanges = true;
        }

        if (request.getTelefone() != null && !Objects.equals(usuarioToUpdate.getTelefone(), request.getTelefone())) {
            usuarioToUpdate.setTelefone(request.getTelefone());
            hasChanges = true;
        }

        return hasChanges;
    }

    private boolean isUpdateLoginValid(UpdateUsuarioRequest request, UsuarioResponse usuario) {
        boolean isUpdateLoginValid = false;

        if (request.getEmail() != null && !Objects.equals(request.getEmail(), usuario.getEmail())) {
            isUpdateLoginValid = true;
        }

        if (request.getSenha() != null) {
            isUpdateLoginValid = true;
        }

        if (request.getIdPapel() != null && !Objects.equals(usuario.getIdPapel(), request.getIdPapel())) {
            validatePapel(request.getIdPapel());
            isUpdateLoginValid = true;
        }

        return isUpdateLoginValid;
    }
}