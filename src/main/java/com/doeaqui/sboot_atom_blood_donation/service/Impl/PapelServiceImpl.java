package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.Papel;
import com.doeaqui.sboot_atom_blood_donation.repository.PapelRepository;
import com.doeaqui.sboot_atom_blood_donation.service.PapelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PapelServiceImpl implements PapelService {

    private final PapelRepository repository;

    @Override
    public List<Papel> getPapeisUsuarios() {
        return repository.getPapeisUsuarios();
    }
}