package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.config.exception.ResourceNotFoundException;
import com.doeaqui.sboot_atom_blood_donation.domain.Hemocentro;
import com.doeaqui.sboot_atom_blood_donation.mapper.HemocentroMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewHemocentroRequest;
import com.doeaqui.sboot_atom_blood_donation.repository.HemocentroRepository;
import com.doeaqui.sboot_atom_blood_donation.service.HemocentroService;
import com.doeaqui.sboot_atom_blood_donation.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HemocentroServiceImpl implements HemocentroService {

    private final HemocentroRepository repository;
    private final HemocentroMapper mapper;

    @Override
    @Transactional
    public Hemocentro postNewHemocentro(NewHemocentroRequest request) {
        Hemocentro newHemocentro = mapper.toHemocentro(request);
        newHemocentro.setAtivo(Boolean.FALSE);
        int idNewHemocentro = repository.postNewHemocentro(newHemocentro);
        newHemocentro.setId(idNewHemocentro);
        return newHemocentro;
    }

    @Override
    public Hemocentro getHemocentroInfoById(Integer idHemocentro) {
        Optional<Hemocentro> optionalHemocentro = repository.getHemocentroInfoById(idHemocentro);
        if (optionalHemocentro.isEmpty()) throw new ResourceNotFoundException("Hemocentro não encontrado.");
        return optionalHemocentro.get();
    }

    @Override
    public List<Hemocentro> getHemocentroByFilter(String nome, String telefone, String email) {
        AppUtils.requireAtLeastOneNonNull(Arrays.asList(nome, telefone, email));
        if (nome != null && !nome.trim().isEmpty()) nome = "%" + nome + "%";
        return repository.getHemocentroByFilter(nome, telefone, email);
    }
}