package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.Hemocentro;
import com.doeaqui.sboot_atom_blood_donation.mapper.HemocentroMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewHemocentroRequest;
import com.doeaqui.sboot_atom_blood_donation.repository.HemocentroRepository;
import com.doeaqui.sboot_atom_blood_donation.service.HemocentroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HemocentroServiceImpl implements HemocentroService {

    private final HemocentroRepository repository;
    private final HemocentroMapper mapper;

    @Override
    @Transactional
    public Hemocentro postNewHemocentro(NewHemocentroRequest request) {
        Hemocentro newHemocentro = mapper.toHemocentro(request);
        newHemocentro.setAtivo(Boolean.FALSE);
        int idNewHemocentro = repository.postNewHemocentro(newHemocentro);
        newHemocentro.setId(idNewHemocentro);
        return newHemocentro;
    }
}