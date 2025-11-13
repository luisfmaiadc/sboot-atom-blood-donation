package com.doeaqui.sboot_doe_aqui_monolith.service.Impl;

import com.doeaqui.sboot_doe_aqui_monolith.config.exception.ResourceNotFoundException;
import com.doeaqui.sboot_doe_aqui_monolith.config.security.CustomUserDetails;
import com.doeaqui.sboot_doe_aqui_monolith.domain.EnderecoHemocentro;
import com.doeaqui.sboot_doe_aqui_monolith.domain.Hemocentro;
import com.doeaqui.sboot_doe_aqui_monolith.mapper.HemocentroMapper;
import com.doeaqui.sboot_doe_aqui_monolith.model.NewHemocentroRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UpdateHemocentroRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.UsuarioResponse;
import com.doeaqui.sboot_doe_aqui_monolith.repository.EnderecoHemocentroRepository;
import com.doeaqui.sboot_doe_aqui_monolith.repository.HemocentroRepository;
import com.doeaqui.sboot_doe_aqui_monolith.service.HemocentroService;
import com.doeaqui.sboot_doe_aqui_monolith.service.TipoSanguineoService;
import com.doeaqui.sboot_doe_aqui_monolith.service.UsuarioService;
import com.doeaqui.sboot_doe_aqui_monolith.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HemocentroServiceImpl implements HemocentroService {

    private final HemocentroRepository repository;
    private final EnderecoHemocentroRepository enderecoHemocentroRepository;
    private final HemocentroMapper mapper;
    private final UsuarioService usuarioService;
    private final TipoSanguineoService tipoSanguineoService;

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

    @Override
    public List<Hemocentro> getHemocentroByLocation(Double latitude, Double longitude, Integer raio) {
        List<Hemocentro> nearbyHemocentros = findNearbyHemocentros(latitude, longitude, raio);
        if (nearbyHemocentros.isEmpty()) {
            return List.of();
        }

        UsuarioResponse currentUser = getCurrentUser();
        return sortHemocentrosByPriority(nearbyHemocentros, currentUser);
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

    private List<Hemocentro> findNearbyHemocentros(Double latitude, Double longitude, Integer raio) {
        Point userLocation = new Point(longitude, latitude);
        Distance distance = new Distance(raio, Metrics.KILOMETERS);
        List<EnderecoHemocentro> enderecoHemocentroList = enderecoHemocentroRepository.findByGeoLocationNear(userLocation, distance);

        return enderecoHemocentroList.stream()
                .map(endereco -> {
                    Hemocentro hemocentro = getHemocentroInfoById(endereco.getId());
                    hemocentro.setEndereco(endereco);
                    return hemocentro;
                })
                .toList();
    }

    private List<Hemocentro> sortHemocentrosByPriority(List<Hemocentro> hemocentros, UsuarioResponse usuario) {
        List<Integer> hemocentroIdList = hemocentros.stream().map(Hemocentro::getId).toList();
        List<Byte> tipoSanguineoIdList = tipoSanguineoService.getTipoSanguineoCompativel(usuario.getIdTipoSanguineo().byteValue());
        Set<Integer> hemocentrosPrioridadeIdList = repository.getHemocentroIfHasSolicitacaoDoacao(hemocentroIdList, tipoSanguineoIdList);

        if (hemocentrosPrioridadeIdList.isEmpty()) {
            return hemocentros;
        }

        List<Hemocentro> sortedHemocentros = new ArrayList<>(hemocentros);
        Set<Integer> prioridadeIdSet = new HashSet<>(hemocentrosPrioridadeIdList);
        sortedHemocentros.sort(Comparator.comparing(h -> !prioridadeIdSet.contains(h.getId())));
        return sortedHemocentros;
    }

    private UsuarioResponse getCurrentUser() {
        CustomUserDetails userDetails = AppUtils.getUserDetails();
        return usuarioService.getUserInfoById(userDetails.getIdUsuario());
    }
}