package com.doeaqui.sboot_atom_blood_donation.controller;

import com.doeaqui.sboot_atom_blood_donation.api.UsuarioApiDelegate;
import com.doeaqui.sboot_atom_blood_donation.model.NewUsuarioRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UpdateUsuarioRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UsuarioResponse;
import com.doeaqui.sboot_atom_blood_donation.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UsuarioApiImpl implements UsuarioApiDelegate {

    private final UsuarioService usuarioService;

    @Override
    public ResponseEntity<UsuarioResponse> postNewUser(NewUsuarioRequest usuarioRequest) {
        UsuarioResponse response  = usuarioService.postNewUser(usuarioRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.idUsuario == #idUsuario")
    public ResponseEntity<UsuarioResponse> getUserInfoById(Integer idUsuario) {
        UsuarioResponse response = usuarioService.getUserInfoById(idUsuario);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.idUsuario == #idUsuario")
    public ResponseEntity<UsuarioResponse> patchUserInfo(Integer idUsuario, UpdateUsuarioRequest updateUsuarioRequest) {
        UsuarioResponse response = usuarioService.patchUserInfo(idUsuario, updateUsuarioRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(Integer idUsuario) {
        usuarioService.deleteUser(idUsuario);
        return ResponseEntity.noContent().build();
    }
}