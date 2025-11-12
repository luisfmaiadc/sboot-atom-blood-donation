package com.doeaqui.sboot_doe_aqui_monolith.service.Impl;

import com.doeaqui.sboot_doe_aqui_monolith.config.exception.ResourceNotFoundException;
import com.doeaqui.sboot_doe_aqui_monolith.domain.EnderecoHemocentro;
import com.doeaqui.sboot_doe_aqui_monolith.domain.Hemocentro;
import com.doeaqui.sboot_doe_aqui_monolith.mapper.HemocentroMapper;
import com.doeaqui.sboot_doe_aqui_monolith.model.NewHemocentroRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UpdateHemocentroRequest;
import com.doeaqui.sboot_doe_aqui_monolith.repository.EnderecoHemocentroRepository;
import com.doeaqui.sboot_doe_aqui_monolith.repository.HemocentroRepository;
import com.doeaqui.sboot_doe_aqui_monolith.service.HemocentroService;
import com.doeaqui.sboot_doe_aqui_monolith.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HemocentroServiceImpl implements HemocentroService {

    private final HemocentroRepository repository;
    private final EnderecoHemocentroRepository enderecoHemocentroRepository;
    private final HemocentroMapper mapper;

    @Override
    @Transactional
    public Hemocentro postNewHemocentro(NewHemocentroRequest request) {
        Hemocentro newHemocentro = mapper.toHemocentro(request);
        newHemocentro.setAtivo(Boolean.TRUE);
        int idNewHemocentro = repository.postNewHemocentro(newHemocentro);
        newHemocentro.getEndereco().setId(idNewHemocentro);
        enderecoHemocentroRepository.save(newHemocentro.getEndereco());
        return getHemocentroInfoById(idNewHemocentro);
    }

    @Override
    public Hemocentro getHemocentroInfoById(Integer idHemocentro) {
        Optional<Hemocentro> optionalHemocentro = repository.getHemocentroInfoById(idHemocentro);
        if (optionalHemocentro.isEmpty()) throw new ResourceNotFoundException("Hemocentro não encontrado.");
        Hemocentro hemocentro = optionalHemocentro.get();
        EnderecoHemocentro endereco = getEnderecoHemocentro(hemocentro);
        hemocentro.setEndereco(endereco);
        return hemocentro;
    }

    @Override
    public List<Hemocentro> getHemocentroByFilter(String nome, String telefone, String email) {
        AppUtils.requireAtLeastOneNonNull(Arrays.asList(nome, telefone, email));
        if (nome != null && !nome.trim().isEmpty()) nome = "%" + nome + "%";
        List<Hemocentro> hemocentroList = repository.getHemocentroByFilter(nome, telefone, email);
        if (hemocentroList.isEmpty()) return hemocentroList;
        hemocentroList.forEach(h -> {
            EnderecoHemocentro endereco = getEnderecoHemocentro(h);
            h.setEndereco(endereco);
        });
        return hemocentroList;
    }

    @Override
    @Transactional
    public Hemocentro patchHemocentroInfo(Integer idHemocentro, UpdateHemocentroRequest updateHemocentroRequest) {
        AppUtils.requireAtLeastOneNonNull(Arrays.asList(updateHemocentroRequest.getTelefone(),
                updateHemocentroRequest.getEmail(), updateHemocentroRequest.getAtivo()));

        Hemocentro hemocentro = getHemocentroInfoById(idHemocentro);

        boolean hemocentroFieldsChanged = applyHemocentroUpdates(hemocentro, updateHemocentroRequest);
        if(!hemocentroFieldsChanged) throw new IllegalArgumentException("Informe ao menos um campo para atualizar.");

        repository.patchHemocentroInfo(hemocentro);
        return getHemocentroInfoById(idHemocentro);
    }

    @Override
    @Transactional
    public void deleteHemocentro(Integer idHemocentro) {
        Hemocentro hemocentro = getHemocentroInfoById(idHemocentro);
        if(Objects.equals(hemocentro.getAtivo(), Boolean.FALSE)) throw new IllegalArgumentException("Hemocentro já inativado.");
        repository.deleteHemocentro(idHemocentro);
    }

    private boolean applyHemocentroUpdates(Hemocentro hemocentro, UpdateHemocentroRequest updateHemocentroRequest) {
        boolean hasChanges = false;

        if (updateHemocentroRequest.getTelefone() != null
                && !Objects.equals(hemocentro.getTelefone(), updateHemocentroRequest.getTelefone())) {
            hemocentro.setTelefone(updateHemocentroRequest.getTelefone());
            hasChanges = true;
        }

        if (updateHemocentroRequest.getEmail() != null
                && !Objects.equals(hemocentro.getEmail(), updateHemocentroRequest.getEmail())) {
            hemocentro.setEmail(updateHemocentroRequest.getEmail());
            hasChanges = true;
        }

        if (updateHemocentroRequest.getAtivo() != null
                && !Objects.equals(hemocentro.getAtivo(), updateHemocentroRequest.getAtivo())) {
            hemocentro.setAtivo(updateHemocentroRequest.getAtivo());
            hasChanges = true;
        }

        return hasChanges;
    }

    private EnderecoHemocentro getEnderecoHemocentro(Hemocentro hemocentro) {
        Optional<EnderecoHemocentro> optionalEndereco = enderecoHemocentroRepository.findById(hemocentro.getId());
        if (optionalEndereco.isEmpty()) throw new ResourceNotFoundException("Endereço do hemocentro não encontrado.");
        return optionalEndereco.get();
    }
}