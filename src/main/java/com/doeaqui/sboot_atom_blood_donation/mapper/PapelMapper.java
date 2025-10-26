package com.doeaqui.sboot_atom_blood_donation.mapper;

import com.doeaqui.sboot_atom_blood_donation.domain.Papel;
import com.doeaqui.sboot_atom_blood_donation.model.PapelResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PapelMapper {

    List<PapelResponse> toPapelResponseList(List<Papel> papelList);
}