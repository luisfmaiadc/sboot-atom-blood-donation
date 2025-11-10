package com.doeaqui.sboot_doe_aqui_monolith.service;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Doacao;
import com.doeaqui.sboot_doe_aqui_monolith.model.NewDoacaoRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UpdateDoacaoRequest;

import java.time.LocalDate;
import java.util.List;

public interface DoacaoService {

    Doacao postNewDoacao(NewDoacaoRequest newDoacaoRequest);
    Doacao getDoacaoInfoById(Integer idDoacao);
    List<Doacao> getDoacaoByFilter(Integer idUsuario, Integer idHemocentro, LocalDate dataDoacao, Integer volume);
    Doacao patchDoacaoInfo(Integer idDoacao, UpdateDoacaoRequest updateDoacaoRequest);
}