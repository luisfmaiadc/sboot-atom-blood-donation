package com.doeaqui.sboot_atom_blood_donation.repository;

import com.doeaqui.sboot_atom_blood_donation.domain.TipoSanguineo;

import java.util.List;

public interface TipoSanguineoRepository {

    List<TipoSanguineo> getTiposSanguineos();
}