package com.doeaqui.sboot_doe_aqui_monolith.repository;

import com.doeaqui.sboot_doe_aqui_monolith.domain.SolicitacaoDoacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SolicitacaoRepository {

    int postNewSolicitacaoDoacao(SolicitacaoDoacao solicitacaoDoacao);
    boolean isSolicitacaoDoacaoValid(Integer idUsuario);
    List<SolicitacaoDoacao> getSolicitacaoDoacaoByFilter(Integer idUsuario, Integer idHemocentro, Integer idTipoSanguineo, LocalDate dataSolicitacao, String status, LocalDate dataEncerramento);
    Optional<SolicitacaoDoacao> getSolicitacaoDoacaoInfoById(Integer idSolicitacaoDoacao);
    void patchSolicitacaoDoacaoInfo(SolicitacaoDoacao solicitacaoDoacao);
}