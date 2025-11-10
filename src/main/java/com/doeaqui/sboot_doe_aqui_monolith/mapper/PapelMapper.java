package com.doeaqui.sboot_doe_aqui_monolith.mapper;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Papel;
import com.doeaqui.sboot_doe_aqui_monolith.model.PapelResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PapelMapper {

    List<PapelResponse> toPapelResponseList(List<Papel> papelList);
}