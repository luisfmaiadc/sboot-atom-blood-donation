package com.doeaqui.sboot_atom_blood_donation.repository;

import com.doeaqui.sboot_atom_blood_donation.domain.Hemocentro;

import java.util.List;
import java.util.Optional;

public interface HemocentroRepository {

    int postNewHemocentro(Hemocentro newHemocentro);
    Optional<Hemocentro> getHemocentroInfoById(Integer idHemocentro);
    List<Hemocentro> getHemocentroByFilter(String nome, String telefone, String email);
    void patchHemocentroInfo(Hemocentro hemocentro);
    void deleteHemocentro(Integer idHemocentro);
}