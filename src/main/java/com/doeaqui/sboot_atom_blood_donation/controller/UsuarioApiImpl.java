package com.doeaqui.sboot_atom_blood_donation.controller;

import com.doeaqui.sboot_atom_blood_donation.api.UsuarioApiDelegate;
import com.doeaqui.sboot_atom_blood_donation.domain.Usuario;
import com.doeaqui.sboot_atom_blood_donation.mapper.UsuarioMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewUsuarioRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UpdateUsuarioRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UsuarioResponse;
import com.doeaqui.sboot_atom_blood_donation.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UsuarioApiImpl implements UsuarioApiDelegate {

    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    @Override
    public ResponseEntity<UsuarioResponse> postNewUser(NewUsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioService.postNewUser(usuarioRequest);
        UsuarioResponse response = mapper.toUsuarioResponse(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    public ResponseEntity<UsuarioResponse> getUserInfoById(Integer idUsuario) {
        UsuarioResponse response = usuarioService.getUserInfoById(idUsuario);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UsuarioResponse> patchUserInfo(Integer idUsuario, UpdateUsuarioRequest updateUsuarioRequest) {
        UsuarioResponse response = usuarioService.patchUserInfo(idUsuario, updateUsuarioRequest);
        return ResponseEntity.ok(response);
    }
}