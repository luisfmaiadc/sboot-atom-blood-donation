package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.config.exception.ResourceNotFoundException;
import com.doeaqui.sboot_atom_blood_donation.domain.SolicitacaoDoacao;
import com.doeaqui.sboot_atom_blood_donation.domain.Status;
import com.doeaqui.sboot_atom_blood_donation.mapper.SolicitacaoDoacaoMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewSolicitacaoDoacaoRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UpdateSolicitacaoDoacaoRequest;
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
import java.util.Objects;
import java.util.stream.Stream;
import java.util.Optional;

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
    public List<SolicitacaoDoacao> getSolicitacaoDoacaoByFilter(Integer idUsuario, Integer idHemocentro, Integer idTipoSanguineo, LocalDate dataSolicitacao, String status, LocalDate dataEncerramento) {
        AppUtils.requireAtLeastOneNonNull(Arrays.asList(idUsuario, idHemocentro, idTipoSanguineo, dataSolicitacao, status, dataEncerramento));
        return repository.getSolicitacaoDoacaoByFilter(idUsuario, idHemocentro, idTipoSanguineo, dataSolicitacao, status, dataEncerramento);
    }

    @Override
    public SolicitacaoDoacao getSolicitacaoDoacaoInfoById(Integer idSolicitacaoDoacao) {
        Optional<SolicitacaoDoacao> optionalSolicitacaoDoacao = repository.getSolicitacaoDoacaoInfoById(idSolicitacaoDoacao);
        if (optionalSolicitacaoDoacao.isEmpty()) throw new ResourceNotFoundException("Solicitação de doação não encontrada.");
        return optionalSolicitacaoDoacao.get();
    }

    @Override
    @Transactional
    public SolicitacaoDoacao patchSolicitacaoDoacaoInfo(Integer idSolicitacaoDoacao, UpdateSolicitacaoDoacaoRequest updateSolicitacaoRequest) {
        AppUtils.requireAtLeastOneNonNull(Arrays.asList(updateSolicitacaoRequest.getStatus(), updateSolicitacaoRequest.getObservacoes()));
        SolicitacaoDoacao solicitacaoDoacao = getSolicitacaoDoacaoInfoById(idSolicitacaoDoacao);

        if (solicitacaoDoacao.getStatus().equals(Status.ENCERRADA) || solicitacaoDoacao.getStatus().equals(Status.CANCELADA))
            throw new IllegalArgumentException("Não é possível atualizar uma solicitação após seu cancelamento ou encerramento.");

        boolean solicitacaoDoacaoFieldsChanged = applySolicitacaoDoacaoUpdates(updateSolicitacaoRequest, solicitacaoDoacao);
        if (!solicitacaoDoacaoFieldsChanged) throw new IllegalArgumentException("Informe ao menos um campo para atualizar ou os valores informados são os mesmos dos atuais.");

        repository.patchSolicitacaoDoacaoInfo(solicitacaoDoacao);
        return getSolicitacaoDoacaoInfoById(idSolicitacaoDoacao);
    }

    private boolean applySolicitacaoDoacaoUpdates(UpdateSolicitacaoDoacaoRequest updateSolicitacaoRequest, SolicitacaoDoacao solicitacaoDoacao) {
        boolean hasChanges = false;

        if (updateSolicitacaoRequest.getStatus() != null) {
            Status newStatus = validateAndGetStatus(updateSolicitacaoRequest.getStatus());

            if (!Objects.equals(newStatus, solicitacaoDoacao.getStatus())) {
                if (newStatus.equals(Status.ABERTA) && solicitacaoDoacao.getStatus().equals(Status.EM_ANDAMENTO)) {
                    throw new IllegalArgumentException("Não é possível retornar o status de 'EM_ANDAMENTO' para 'ABERTA'.");
                }

                if (newStatus.equals(Status.ENCERRADA) || newStatus.equals(Status.CANCELADA)) {
                    solicitacaoDoacao.setDataEncerramento(LocalDateTime.now());
                }

                solicitacaoDoacao.setStatus(newStatus);
                hasChanges = true;
            }
        }

        if (updateSolicitacaoRequest.getObservacoes() != null && !Objects.equals(solicitacaoDoacao.getObservacoes(), updateSolicitacaoRequest.getObservacoes())) {
            solicitacaoDoacao.setObservacoes(updateSolicitacaoRequest.getObservacoes());
            hasChanges = true;
        }

        return hasChanges;
    }

    private Status validateAndGetStatus(String statusStr) {
        return Stream.of(Status.values())
                .filter(s -> s.name().equalsIgnoreCase(statusStr))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status informado inválido: " + statusStr + "."));
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