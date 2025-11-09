package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.TipoSanguineo;
import com.doeaqui.sboot_atom_blood_donation.repository.TipoSanguineoRepository;
import com.doeaqui.sboot_atom_blood_donation.service.TipoSanguineoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TipoSanguineoServiceImpl implements TipoSanguineoService {

    private final TipoSanguineoRepository repository;

    private static final Map<String, Set<String>> COMPATIBILIDADE_SANGUINEA = Map.of(
            "A+", Set.of("A+", "A-", "O+", "O-"),
            "A-", Set.of("A-", "O-"),
            "B+", Set.of("B+", "B-", "O+", "O-"),
            "B-", Set.of("B-", "O-"),
            "AB+", Set.of("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"),
            "AB-", Set.of("A-", "B-", "AB-", "O-"),
            "O+", Set.of("O+", "O-"),
            "O-", Set.of("O-")
    );

    @Override
    @Cacheable("tiposSanguineos")
    public List<TipoSanguineo> getTiposSanguineos() {
        return repository.getTiposSanguineos();
    }

    @Override
    public String getTipoSanguineoById(Byte idTipoSanguineo) {
        List<TipoSanguineo> tipoSanguineoList = getTiposSanguineos();
        return tipoSanguineoList.stream()
                .filter(t -> t.getId().equals(idTipoSanguineo))
                .findFirst()
                .map(t -> t.getTipo() + t.getFator())
                .orElseThrow(() -> new IllegalArgumentException("Tipo sanguíneo inválido."));
    }

    @Override
    public void validateBloodCompatible(Byte idReceptor, Byte idDoador) {
        String tipoReceptor = getTipoSanguineoById(idReceptor);
        String tipoDoador = getTipoSanguineoById(idDoador);

        Set<String> doadoresCompativeis = COMPATIBILIDADE_SANGUINEA.get(tipoReceptor);

        if (doadoresCompativeis == null || !doadoresCompativeis.contains(tipoDoador))
            throw new IllegalArgumentException(
                    String.format("Tipo sanguíneo incompatível. Receptor %s não pode receber de %s.", tipoReceptor, tipoDoador)
            );
    }
}