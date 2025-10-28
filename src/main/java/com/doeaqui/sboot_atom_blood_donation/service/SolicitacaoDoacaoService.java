package com.doeaqui.sboot_atom_blood_donation.service;

import com.doeaqui.sboot_atom_blood_donation.domain.SolicitacaoDoacao;
import com.doeaqui.sboot_atom_blood_donation.model.NewSolicitacaoDoacaoRequest;

import java.time.LocalDate;
import java.util.List;

public interface SolicitacaoDoacaoService {

    SolicitacaoDoacao postNewSolicitacaoDoacao(NewSolicitacaoDoacaoRequest request);
    List<SolicitacaoDoacao> getSolicitacaoDoacaoByFilter(Integer idUsuario, Integer idHemocentro, Integer idTipoSanguineo, LocalDate dataSolicitacao, String status, LocalDate dataEncerramento);
}