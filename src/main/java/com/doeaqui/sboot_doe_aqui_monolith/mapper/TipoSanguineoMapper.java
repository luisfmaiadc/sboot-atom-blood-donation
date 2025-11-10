package com.doeaqui.sboot_doe_aqui_monolith.mapper;

import com.doeaqui.sboot_doe_aqui_monolith.domain.TipoSanguineo;
import com.doeaqui.sboot_doe_aqui_monolith.model.TipoSanguineoResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoSanguineoMapper {

    List<TipoSanguineoResponse> toTipoSanguineoResponseList(List<TipoSanguineo> tipoSanguineoList);
}