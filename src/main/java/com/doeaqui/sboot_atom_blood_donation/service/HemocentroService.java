package com.doeaqui.sboot_atom_blood_donation.service;

import com.doeaqui.sboot_atom_blood_donation.domain.Hemocentro;
import com.doeaqui.sboot_atom_blood_donation.model.NewHemocentroRequest;

public interface HemocentroService {

    Hemocentro postNewHemocentro(NewHemocentroRequest request);
    Hemocentro getHemocentroInfoById(Integer idHemocentro);
}