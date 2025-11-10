package com.doeaqui.sboot_doe_aqui_monolith.repository;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Usuario;
import com.doeaqui.sboot_doe_aqui_monolith.model.UsuarioResponse;

import java.util.Optional;

public interface UsuarioRepository {

    int postNewUser(Usuario usuario);
    Optional<UsuarioResponse> getUserInfoById(Integer id);
    void patchUserInfo(Usuario usuario);
    void deleteUser(Integer idUsuario);
}