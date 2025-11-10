package com.doeaqui.sboot_doe_aqui_monolith.repository;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Doacao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DoacaoRepository {

    int postNewDoacao(Doacao doacao);
    Optional<Doacao> getUltimaDoacao(Integer idUsuario);
    Optional<Doacao> getDoacaoInfoById(Integer idDoacao);
    List<Doacao> getDoacaoByFilter(Integer idHemocentro, LocalDate dataDoacao, Integer volume, Integer idUsuario);
    void patchDoacaoInfo(Doacao doacao);
}