package com.doeaqui.sboot_atom_blood_donation.mapper;

import com.doeaqui.sboot_atom_blood_donation.domain.SolicitacaoDoacao;
import com.doeaqui.sboot_atom_blood_donation.model.NewSolicitacaoDoacaoRequest;
import com.doeaqui.sboot_atom_blood_donation.model.SolicitacaoDoacaoResponse;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SolicitacaoDoacaoMapper {

    SolicitacaoDoacao toSolicitacaoDoacao(NewSolicitacaoDoacaoRequest request);
    SolicitacaoDoacaoResponse toSolicitacaoDoacaoResponse(SolicitacaoDoacao solicitacaoDoacao);
    List<SolicitacaoDoacaoResponse> toSolicitacaoDoacaoResponseList(List<SolicitacaoDoacao> solicitacaoDoacaoList);

    default OffsetDateTime map(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atOffset(ZoneOffset.of("-03:00"));
    }

    default LocalDateTime map(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.toLocalDateTime();
    }
}