package com.doeaqui.sboot_atom_blood_donation.controller;

import com.doeaqui.sboot_atom_blood_donation.api.PapelApiDelegate;
import com.doeaqui.sboot_atom_blood_donation.domain.Papel;
import com.doeaqui.sboot_atom_blood_donation.mapper.PapelMapper;
import com.doeaqui.sboot_atom_blood_donation.model.PapelResponse;
import com.doeaqui.sboot_atom_blood_donation.service.PapelService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PapelApiImpl implements PapelApiDelegate {

    private final PapelService service;
    private final PapelMapper mapper;

    @Override
    @Cacheable("papeisUsuarios")
    public ResponseEntity<List<PapelResponse>> getPapeisUsuarios() {
        List<Papel> papelList = service.getPapeisUsuarios();
        List<PapelResponse> responseList = mapper.toPapelResponseList(papelList);
        return ResponseEntity.ok(responseList);
    }
}