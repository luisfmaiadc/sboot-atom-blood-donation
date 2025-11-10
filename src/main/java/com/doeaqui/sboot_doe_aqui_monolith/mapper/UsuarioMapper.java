package com.doeaqui.sboot_doe_aqui_monolith.mapper;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Usuario;
import com.doeaqui.sboot_doe_aqui_monolith.model.NewUsuarioRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UsuarioResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toUsuario (NewUsuarioRequest usuarioRequest);
    UsuarioResponse toUsuarioResponse(Usuario usuario);
    Usuario toUsuarioFromResponse(UsuarioResponse response);
}