package com.doeaqui.sboot_atom_blood_donation.controller;

import com.doeaqui.sboot_atom_blood_donation.api.TipoSanguineoApiDelegate;
import com.doeaqui.sboot_atom_blood_donation.domain.TipoSanguineo;
import com.doeaqui.sboot_atom_blood_donation.mapper.TipoSanguineoMapper;
import com.doeaqui.sboot_atom_blood_donation.model.TipoSanguineoResponse;
import com.doeaqui.sboot_atom_blood_donation.service.TipoSanguineoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TipoSanguineoApiImpl implements TipoSanguineoApiDelegate {

    private final TipoSanguineoService service;
    private final TipoSanguineoMapper mapper;

    @Override
    public ResponseEntity<List<TipoSanguineoResponse>> getTiposSanguineos() {
        List<TipoSanguineo> tipoSanguineoList = service.getTiposSanguineos();
        List<TipoSanguineoResponse> responseList = mapper.toTipoSanguineoResponseList(tipoSanguineoList);
        return ResponseEntity.ok(responseList);
    }
}