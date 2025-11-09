package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.config.security.CustomUserDetails;
import com.doeaqui.sboot_atom_blood_donation.domain.Doacao;
import com.doeaqui.sboot_atom_blood_donation.mapper.DoacaoMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewDoacaoRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UsuarioResponse;
import com.doeaqui.sboot_atom_blood_donation.repository.DoacaoRepository;
import com.doeaqui.sboot_atom_blood_donation.service.DoacaoService;
import com.doeaqui.sboot_atom_blood_donation.service.HemocentroService;
import com.doeaqui.sboot_atom_blood_donation.service.UsuarioService;
import com.doeaqui.sboot_atom_blood_donation.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DoacaoServiceImpl implements DoacaoService {

    private final HemocentroService hemocentroService;
    private final UsuarioService usuarioService;
    private final DoacaoRepository repository;
    private final DoacaoMapper mapper;

    @Override
    @Transactional
    public Doacao postNewDoacao(NewDoacaoRequest doacaoRequest) {
        Doacao newDoacao = mapper.toDoacao(doacaoRequest);
        CustomUserDetails userDetails = AppUtils.getUserDetails();
        UsuarioResponse usuario = usuarioService.getUserInfoById(userDetails.getIdUsuario());
        isNewDoacaoValid(newDoacao, usuario);
        newDoacao.setIdUsuario(userDetails.getIdUsuario());
        newDoacao.setDataDoacao(LocalDateTime.now());
        int generatedId = repository.postNewDoacao(newDoacao);
        newDoacao.setId(generatedId);
        return newDoacao;
    }

    private void isNewDoacaoValid(Doacao doacao, UsuarioResponse usuario) {
        if (doacao.getVolume() != null && (doacao.getVolume() < 0 || doacao.getVolume() > 500)) {
            throw new IllegalArgumentException("Volume de sangue da doação é inválido.");
        }

        repository.getUltimaDoacao(usuario.getId()).ifPresent(ultimaDoacao -> {
            int mesesDeEspera = "M".equals(usuario.getGenero()) ? 2 : 3;
            LocalDateTime dataMinimaParaDoar = LocalDateTime.now().minusMonths(mesesDeEspera);

            if (ultimaDoacao.getDataDoacao().isAfter(dataMinimaParaDoar)) {
                throw new IllegalArgumentException("Intervalo mínimo entre doações não atingido. Aguarde pelo o menos " + mesesDeEspera + " meses desde a última doação para realizar uma nova.");
            }
        });

        hemocentroService.getHemocentroInfoById(doacao.getIdHemocentro());
    }
}