package com.doeaqui.sboot_atom_blood_donation.service;

import com.doeaqui.sboot_atom_blood_donation.domain.Hemocentro;
import com.doeaqui.sboot_atom_blood_donation.model.NewHemocentroRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UpdateHemocentroRequest;

import java.util.List;

public interface HemocentroService {

    Hemocentro postNewHemocentro(NewHemocentroRequest request);
    Hemocentro getHemocentroInfoById(Integer idHemocentro);
    List<Hemocentro> getHemocentroByFilter(String nome, String telefone, String email);
    Hemocentro patchHemocentroInfo(Integer idHemocentro, UpdateHemocentroRequest updateHemocentroRequest);
    void deleteHemocentro(Integer idHemocentro);
}