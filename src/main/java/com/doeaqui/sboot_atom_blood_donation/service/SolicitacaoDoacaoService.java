package com.doeaqui.sboot_atom_blood_donation.service;

import com.doeaqui.sboot_atom_blood_donation.domain.SolicitacaoDoacao;
import com.doeaqui.sboot_atom_blood_donation.model.NewSolicitacaoDoacaoRequest;

public interface SolicitacaoDoacaoService {

    SolicitacaoDoacao postNewSolicitacaoDoacao(NewSolicitacaoDoacaoRequest request);
}