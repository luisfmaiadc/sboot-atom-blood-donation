package com.doeaqui.sboot_atom_blood_donation.service;

import com.doeaqui.sboot_atom_blood_donation.domain.Doacao;
import com.doeaqui.sboot_atom_blood_donation.model.NewDoacaoRequest;

public interface DoacaoService {

    Doacao postNewDoacao(NewDoacaoRequest newDoacaoRequest);
    Doacao getDoacaoInfoById(Integer idDoacao);
}