package com.doeaqui.sboot_atom_blood_donation.controller;

import com.doeaqui.sboot_atom_blood_donation.api.HemocentroApiDelegate;
import com.doeaqui.sboot_atom_blood_donation.domain.Hemocentro;
import com.doeaqui.sboot_atom_blood_donation.mapper.HemocentroMapper;
import com.doeaqui.sboot_atom_blood_donation.model.HemocentroResponse;
import com.doeaqui.sboot_atom_blood_donation.model.NewHemocentroRequest;
import com.doeaqui.sboot_atom_blood_donation.service.HemocentroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class HemocentroApiImpl implements HemocentroApiDelegate {

    private final HemocentroService service;
    private final HemocentroMapper mapper;

    @Override
    public ResponseEntity<HemocentroResponse> postNewHemocentro(NewHemocentroRequest newHemocentroRequest) {
        Hemocentro newHemocentro = service.postNewHemocentro(newHemocentroRequest);
        HemocentroResponse response = mapper.toHemocentroResponse(newHemocentro);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    public ResponseEntity<HemocentroResponse> getHemocentroInfoById(Integer idHemocentro) {
        Hemocentro hemocentro = service.getHemocentroInfoById(idHemocentro);
        HemocentroResponse response = mapper.toHemocentroResponse(hemocentro);
        return ResponseEntity.ok(response);
    }
}