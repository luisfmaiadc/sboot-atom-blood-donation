package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.SolicitacaoDoacao;
import com.doeaqui.sboot_atom_blood_donation.domain.Status;
import com.doeaqui.sboot_atom_blood_donation.mapper.SolicitacaoDoacaoMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewSolicitacaoDoacaoRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UsuarioResponse;
import com.doeaqui.sboot_atom_blood_donation.repository.SolicitacaoRepository;
import com.doeaqui.sboot_atom_blood_donation.service.SolicitacaoDoacaoService;
import com.doeaqui.sboot_atom_blood_donation.service.UsuarioService;
import com.doeaqui.sboot_atom_blood_donation.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitacaoDoacaoServiceImpl implements SolicitacaoDoacaoService {

    private final SolicitacaoRepository repository;
    private final SolicitacaoDoacaoMapper mapper;
    private final UsuarioService service;

    @Override
    @Transactional
    public SolicitacaoDoacao postNewSolicitacaoDoacao(NewSolicitacaoDoacaoRequest request) {
        UsuarioResponse usuario = service.getUserInfoById(request.getIdUsuario());
        isSolicitacaoDoacaoValid(request.getIdUsuario());
        isTipoSanguineoValid(usuario, request.getIdTipoSanguineo());
        SolicitacaoDoacao newSolicitacaoDoacao = mapper.toSolicitacaoDoacao(request);
        newSolicitacaoDoacao.setDataSolicitacao(LocalDateTime.now());
        newSolicitacaoDoacao.setStatus(Status.ABERTA);
        int idSolicitacaoDoacao = repository.postNewSolicitacaoDoacao(newSolicitacaoDoacao);
        newSolicitacaoDoacao.setId(idSolicitacaoDoacao);
        return newSolicitacaoDoacao;
    }

    @Override
    public List<SolicitacaoDoacao> getSolicitacaoDoacaoByFilter(Integer idUsuario, Integer idHemocentro, Integer idTipoSanguineo, LocalDate dataSolicitacao, String status, String dataEncerramento) {
        AppUtils.requireAtLeastOneNonNull(Arrays.asList(idUsuario, idHemocentro, idTipoSanguineo, dataSolicitacao, status, dataEncerramento));
        return repository.getSolicitacaoDoacaoByFilter(idUsuario, idHemocentro, idTipoSanguineo, dataSolicitacao, status, dataEncerramento);
    }

    private void isTipoSanguineoValid(UsuarioResponse usuario, Integer idTipoSanguineo) {
        if (usuario.getIdTipoSanguineo() == null || idTipoSanguineo == null) throw new IllegalArgumentException("O tipo sanguíneo não pode ser nulo.");
        if (!usuario.getIdTipoSanguineo().equals(idTipoSanguineo)) throw new IllegalArgumentException("O tipo sanguíneo informado não corresponde ao do usuário.");
    }

    private void isSolicitacaoDoacaoValid(Integer idUsuario) {
        boolean isNotValid = repository.isSolicitacaoDoacaoValid(idUsuario);
        if (isNotValid) throw new IllegalArgumentException("Já existe uma solicitação de doação em curso para este usuário.");
    }
}