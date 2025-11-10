package com.doeaqui.sboot_atom_blood_donation.service.Impl;

import com.doeaqui.sboot_atom_blood_donation.config.exception.ResourceNotFoundException;
import com.doeaqui.sboot_atom_blood_donation.config.security.CustomUserDetails;
import com.doeaqui.sboot_atom_blood_donation.domain.Doacao;
import com.doeaqui.sboot_atom_blood_donation.domain.SolicitacaoDoacao;
import com.doeaqui.sboot_atom_blood_donation.mapper.DoacaoMapper;
import com.doeaqui.sboot_atom_blood_donation.model.NewDoacaoRequest;
import com.doeaqui.sboot_atom_blood_donation.model.UsuarioResponse;
import com.doeaqui.sboot_atom_blood_donation.repository.DoacaoRepository;
import com.doeaqui.sboot_atom_blood_donation.service.*;
import com.doeaqui.sboot_atom_blood_donation.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoacaoServiceImpl implements DoacaoService {

    private final HemocentroService hemocentroService;
    private final UsuarioService usuarioService;
    private final SolicitacaoDoacaoService solicitacaoDoacaoService;
    private final TipoSanguineoService tipoSanguineoService;
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
        return getDoacaoInfoById(generatedId);
    }

    @Override
    public Doacao getDoacaoInfoById(Integer idDoacao) {
        Optional<Doacao> optionalDoacao = repository.getDoacaoInfoById(idDoacao);
        if (optionalDoacao.isEmpty()) throw new ResourceNotFoundException("Doação não encontrada.");
        CustomUserDetails userDetails = AppUtils.getUserDetails();
        Doacao doacao = optionalDoacao.get();
        boolean isAdmin = AppUtils.isAdmin();
        boolean isOwner = doacao.getIdUsuario().equals(userDetails.getIdUsuario());
        if (!isOwner && !isAdmin) throw new AuthorizationDeniedException("Acesso negado.");
        return doacao;
    }

    @Override
    public List<Doacao> getDoacaoByFilter(Integer idUsuario, Integer idHemocentro, LocalDate dataDoacao, Integer volume) {
        AppUtils.requireAtLeastOneNonNull(Arrays.asList(idUsuario, idHemocentro, dataDoacao, volume));
        CustomUserDetails userDetails = AppUtils.getUserDetails();
        boolean isAdmin = AppUtils.isAdmin();
        if (!isAdmin && idUsuario != null) throw new AuthorizationDeniedException("Apenas um administrador pode visualizar doações de outro usuário.");
        return repository.getDoacaoByFilter(idHemocentro, dataDoacao, volume, isAdmin ? idUsuario : userDetails.getIdUsuario());
    }

    private void isNewDoacaoValid(Doacao doacao, UsuarioResponse usuario) {
        if (doacao.getVolume() != null && (doacao.getVolume() < 0 || doacao.getVolume() > 500)) {
            throw new IllegalArgumentException("Volume de sangue da doação é inválido.");
        }

        if (doacao.getIdSolicitacaoDoacao() != null) {
            SolicitacaoDoacao solicitacaoDoacao = solicitacaoDoacaoService.getSolicitacaoDoacaoInfoById(doacao.getIdSolicitacaoDoacao());
            tipoSanguineoService.validateBloodCompatible(solicitacaoDoacao.getIdTipoSanguineo(), usuario.getIdTipoSanguineo().byteValue());
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