package com.doeaqui.sboot_doe_aqui_monolith.service.Impl;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Papel;
import com.doeaqui.sboot_doe_aqui_monolith.repository.PapelRepository;
import com.doeaqui.sboot_doe_aqui_monolith.service.PapelService;
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