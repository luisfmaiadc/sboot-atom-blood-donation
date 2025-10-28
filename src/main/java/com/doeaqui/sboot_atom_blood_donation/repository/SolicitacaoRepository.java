package com.doeaqui.sboot_atom_blood_donation.repository;

import com.doeaqui.sboot_atom_blood_donation.domain.SolicitacaoDoacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SolicitacaoRepository {

    int postNewSolicitacaoDoacao(SolicitacaoDoacao solicitacaoDoacao);
    boolean isSolicitacaoDoacaoValid(Integer idUsuario);
    List<SolicitacaoDoacao> getSolicitacaoDoacaoByFilter(Integer idUsuario, Integer idHemocentro, Integer idTipoSanguineo, LocalDate dataSolicitacao, String status, LocalDate dataEncerramento);
    Optional<SolicitacaoDoacao> getSolicitacaoDoacaoInfoById(Integer idSolicitacaoDoacao);
}