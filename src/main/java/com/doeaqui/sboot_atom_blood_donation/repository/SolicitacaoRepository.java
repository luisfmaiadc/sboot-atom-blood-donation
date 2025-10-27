package com.doeaqui.sboot_atom_blood_donation.repository;

import com.doeaqui.sboot_atom_blood_donation.domain.SolicitacaoDoacao;

public interface SolicitacaoRepository {

    int postNewSolicitacaoDoacao(SolicitacaoDoacao solicitacaoDoacao);
    boolean isSolicitacaoDoacaoValid(Integer idUsuario);
}