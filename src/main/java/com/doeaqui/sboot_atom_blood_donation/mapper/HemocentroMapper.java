package com.doeaqui.sboot_atom_blood_donation.mapper;

import com.doeaqui.sboot_atom_blood_donation.domain.Hemocentro;
import com.doeaqui.sboot_atom_blood_donation.model.HemocentroResponse;
import com.doeaqui.sboot_atom_blood_donation.model.NewHemocentroRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HemocentroMapper {

    Hemocentro toHemocentro(NewHemocentroRequest request);
    HemocentroResponse toHemocentroResponse(Hemocentro hemocentro);
    List<HemocentroResponse> toHemocentroResponseList(List<Hemocentro> hemocentroList);
}