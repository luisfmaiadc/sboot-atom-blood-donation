package com.doeaqui.sboot_atom_blood_donation.repository;

import com.doeaqui.sboot_atom_blood_donation.domain.Doacao;

import java.util.Optional;

public interface DoacaoRepository {

    int postNewDoacao(Doacao doacao);
    Optional<Doacao> getUltimaDoacao(Integer idUsuario);
}