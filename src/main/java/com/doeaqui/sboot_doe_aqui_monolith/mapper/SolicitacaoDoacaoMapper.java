package com.doeaqui.sboot_doe_aqui_monolith.mapper;

import com.doeaqui.sboot_doe_aqui_monolith.domain.SolicitacaoDoacao;
import com.doeaqui.sboot_doe_aqui_monolith.model.NewSolicitacaoDoacaoRequest;
import com.doeaqui.sboot_doe_aqui_monolith.model.SolicitacaoDoacaoResponse;
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