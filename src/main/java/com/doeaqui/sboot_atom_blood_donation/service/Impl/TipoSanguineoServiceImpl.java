package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.TipoSanguineo;
import com.doeaqui.sboot_atom_blood_donation.repository.TipoSanguineoRepository;
import com.doeaqui.sboot_atom_blood_donation.service.TipoSanguineoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoSanguineoServiceImpl implements TipoSanguineoService {

    private final TipoSanguineoRepository repository;

    @Override
    @Cacheable("tiposSanguineos")
    public List<TipoSanguineo> getTiposSanguineos() {
        return repository.getTiposSanguineos();
    }
}