package com.doeaqui.sboot_doe_aqui_monolith.controller;

import com.doeaqui.sboot_doe_aqui_monolith.api.DoacaoApiDelegate;
import com.doeaqui.sboot_doe_aqui_monolith.domain.Doacao;
import com.doeaqui.sboot_doe_aqui_monolith.mapper.DoacaoMapper;
import com.doeaqui.sboot_doe_aqui_monolith.model.DoacaoResponse;
import com.doeaqui.sboot_doe_aqui_monolith.model.NewDoacaoRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UpdateDoacaoRequest;
import com.doeaqui.sboot_doe_aqui_monolith.service.DoacaoService;
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

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'DOADOR', 'DOADOR_PACIENTE')")
    public ResponseEntity<DoacaoResponse> patchDoacaoInfo(Integer idDoacao, UpdateDoacaoRequest updateDoacaoRequest) {
        Doacao updatedDoacao = service.patchDoacaoInfo(idDoacao, updateDoacaoRequest);
        DoacaoResponse response = mapper.toDoacaoResponse(updatedDoacao);
        return ResponseEntity.ok(response);
    }
}