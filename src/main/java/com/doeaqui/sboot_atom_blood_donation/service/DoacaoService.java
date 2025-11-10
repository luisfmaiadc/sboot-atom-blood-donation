package com.doeaqui.sboot_atom_blood_donation.service;

import com.doeaqui.sboot_atom_blood_donation.domain.Doacao;
import com.doeaqui.sboot_atom_blood_donation.model.NewDoacaoRequest;

import java.time.LocalDate;
import java.util.List;

public interface DoacaoService {

    Doacao postNewDoacao(NewDoacaoRequest newDoacaoRequest);
    Doacao getDoacaoInfoById(Integer idDoacao);
    List<Doacao> getDoacaoByFilter(Integer idUsuario, Integer idHemocentro, LocalDate dataDoacao, Integer volume);
}