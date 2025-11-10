package com.doeaqui.sboot_doe_aqui_monolith.repository;

import com.doeaqui.sboot_doe_aqui_monolith.domain.TipoSanguineo;

import java.util.List;

public interface TipoSanguineoRepository {

    List<TipoSanguineo> getTiposSanguineos();
}