package com.doeaqui.sboot_atom_blood_donation.mapper;

import com.doeaqui.sboot_atom_blood_donation.domain.Usuario;
import com.doeaqui.sboot_atom_blood_donation.model.NewUsuarioRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UsuarioResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toUsuario (NewUsuarioRequest usuarioRequest);
    UsuarioResponse toUsuarioResponse(Usuario usuario);
}