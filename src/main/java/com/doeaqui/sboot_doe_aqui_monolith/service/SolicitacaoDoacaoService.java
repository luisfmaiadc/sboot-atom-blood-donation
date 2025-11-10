package com.doeaqui.sboot_doe_aqui_monolith.service;

import com.doeaqui.sboot_doe_aqui_monolith.domain.SolicitacaoDoacao;
import com.doeaqui.sboot_doe_aqui_monolith.model.NewSolicitacaoDoacaoRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UpdateSolicitacaoDoacaoRequest;

import java.time.LocalDate;
import java.util.List;

public interface SolicitacaoDoacaoService {

    SolicitacaoDoacao postNewSolicitacaoDoacao(NewSolicitacaoDoacaoRequest request);
    List<SolicitacaoDoacao> getSolicitacaoDoacaoByFilter(Integer idUsuario, Integer idHemocentro, Integer idTipoSanguineo, LocalDate dataSolicitacao, String status, LocalDate dataEncerramento);
    SolicitacaoDoacao getSolicitacaoDoacaoInfoById(Integer idSolicitacaoDoacao);
    SolicitacaoDoacao patchSolicitacaoDoacaoInfo(Integer idSolicitacaoDoacao, UpdateSolicitacaoDoacaoRequest updateSolicitacaoRequest);
}