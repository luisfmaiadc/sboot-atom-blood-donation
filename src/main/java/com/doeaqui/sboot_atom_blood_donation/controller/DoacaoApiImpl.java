package com.doeaqui.sboot_atom_blood_donation.controller;

import com.doeaqui.sboot_atom_blood_donation.api.DoacaoApiDelegate;
import com.doeaqui.sboot_atom_blood_donation.domain.Doacao;
import com.doeaqui.sboot_atom_blood_donation.mapper.DoacaoMapper;
import com.doeaqui.sboot_atom_blood_donation.model.DoacaoResponse;
import com.doeaqui.sboot_atom_blood_donation.model.NewDoacaoRequest;
import com.doeaqui.sboot_atom_blood_donation.service.DoacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoacaoApiImpl implements DoacaoApiDelegate {

    private final DoacaoService service;
    private final DoacaoMapper mapper;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOADOR', 'DOADOR_PACIENTE')")
    public ResponseEntity<DoacaoResponse> postNewDoacao(NewDoacaoRequest newDoacaoRequest) {
        Doacao newDoacao = service.postNewDoacao(newDoacaoRequest);
        DoacaoResponse response = mapper.toDoacaoResponse(newDoacao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOADOR', 'DOADOR_PACIENTE')")
    public ResponseEntity<DoacaoResponse> getDoacaoInfoById(Integer idDoacao) {
        Doacao doacao = service.getDoacaoInfoById(idDoacao);
        DoacaoResponse response = mapper.toDoacaoResponse(doacao);
        return ResponseEntity.ok(response);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOADOR', 'DOADOR_PACIENTE')")
    public ResponseEntity<List<DoacaoResponse>> getDoacaoByFilter(Integer idUsuario, Integer idHemocentro, LocalDate dataDoacao, Integer volume) {
        List<Doacao> doacoes = service.getDoacaoByFilter(idUsuario, idHemocentro, dataDoacao, volume);
        List<DoacaoResponse> response = mapper.toDoacaoResponseList(doacoes);
        return ResponseEntity.ok(response);
    }
}