package com.doeaqui.sboot_doe_aqui_monolith.service;

import com.doeaqui.sboot_doe_aqui_monolith.domain.TipoSanguineo;

import java.util.List;

public interface TipoSanguineoService {

    List<TipoSanguineo> getTiposSanguineos();
    String getTipoSanguineoById(Byte idTipoSanguineo);
    void validateBloodCompatible(Byte idReceptor, Byte idDoador);
    List<Byte> getTipoSanguineoCompativel(Byte idTipoSanguineo);
}