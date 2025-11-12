package com.doeaqui.sboot_doe_aqui_monolith.mapper;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Endereco;
import com.doeaqui.sboot_doe_aqui_monolith.domain.EnderecoHemocentro;
import com.doeaqui.sboot_doe_aqui_monolith.model.NewEnderecoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "geoLocation", expression = "java(new GeoJsonPoint(enderecoRequest.getCoordenadas().getLongitude(), enderecoRequest.getCoordenadas().getLatitude()))")
    EnderecoHemocentro toEnderecoHemocentro(NewEnderecoRequest enderecoRequest);

    @Mapping(source = "geoLocation.x", target = "coordenadas.longitude")
    @Mapping(source = "geoLocation.y", target = "coordenadas.latitude")
    com.doeaqui.sboot_doe_aqui_monolith.model.Endereco toEndereco(Endereco endereco);
}