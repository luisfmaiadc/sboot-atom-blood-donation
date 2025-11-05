package com.doeaqui.sboot_atom_blood_donation.controller;

import com.doeaqui.sboot_atom_blood_donation.api.SolicitacaoDoacaoApiDelegate;
import com.doeaqui.sboot_atom_blood_donation.domain.SolicitacaoDoacao;
import com.doeaqui.sboot_atom_blood_donation.mapper.SolicitacaoDoacaoMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewSolicitacaoDoacaoRequest;
import com.doeaqui.sboot_atom_blood_donation.model.SolicitacaoDoacaoResponse;
import com.doeaqui.sboot_atom_blood_donation.model.UpdateSolicitacaoDoacaoRequest;
import com.doeaqui.sboot_atom_blood_donation.service.SolicitacaoDoacaoService;
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
public class SolicitacaoDoacaoApiImpl implements SolicitacaoDoacaoApiDelegate {

    private final SolicitacaoDoacaoService service;
    private final SolicitacaoDoacaoMapper mapper;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE', 'DOADOR_PACIENTE')")
    public ResponseEntity<SolicitacaoDoacaoResponse> postNewSolicitacaoDoacao(NewSolicitacaoDoacaoRequest newSolicitacaoDoacaoRequest) {
        SolicitacaoDoacao solicitacaoDoacao = service.postNewSolicitacaoDoacao(newSolicitacaoDoacaoRequest);
        SolicitacaoDoacaoResponse response = mapper.toSolicitacaoDoacaoResponse(solicitacaoDoacao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SolicitacaoDoacaoResponse>> getSolicitacaoDoacaoByFilter(Integer idUsuario, Integer idHemocentro, Integer idTipoSanguineo, LocalDate dataSolicitacao, String status, LocalDate dataEncerramento) {
        List<SolicitacaoDoacao> solicitacaoDoacaoList = service.getSolicitacaoDoacaoByFilter(idUsuario, idHemocentro, idTipoSanguineo, dataSolicitacao, status, dataEncerramento);
        List<SolicitacaoDoacaoResponse> responseList = mapper.toSolicitacaoDoacaoResponseList(solicitacaoDoacaoList);
        return ResponseEntity.ok(responseList);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SolicitacaoDoacaoResponse> getSolicitacaoDoacaoInfoById(Integer idSolicitacaoDoacao) {
        SolicitacaoDoacao solicitacaoDoacao = service.getSolicitacaoDoacaoInfoById(idSolicitacaoDoacao);
        SolicitacaoDoacaoResponse response = mapper.toSolicitacaoDoacaoResponse(solicitacaoDoacao);
        return ResponseEntity.ok(response);
    }
    
    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'PACIENTE', 'DOADOR_PACIENTE')")
    public ResponseEntity<SolicitacaoDoacaoResponse> patchSolicitacaoDoacaoInfo(Integer idSolicitacaoDoacao, UpdateSolicitacaoDoacaoRequest updateSolicitacaoRequest) {
        SolicitacaoDoacao solicitacaoDoacao = service.patchSolicitacaoDoacaoInfo(idSolicitacaoDoacao, updateSolicitacaoRequest);
        SolicitacaoDoacaoResponse response = mapper.toSolicitacaoDoacaoResponse(solicitacaoDoacao);
        return ResponseEntity.ok(response);
    }
}