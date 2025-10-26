package com.doeaqui.sboot_atom_blood_donation.mapper;

import com.doeaqui.sboot_atom_blood_donation.domain.TipoSanguineo;
import com.doeaqui.sboot_atom_blood_donation.model.TipoSanguineoResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoSanguineoMapper {

    List<TipoSanguineoResponse> toTipoSanguineoResponseList(List<TipoSanguineo> tipoSanguineoList);
}