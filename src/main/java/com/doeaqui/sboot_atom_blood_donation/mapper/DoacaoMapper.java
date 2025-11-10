package com.doeaqui.sboot_atom_blood_donation.mapper;

import com.doeaqui.sboot_atom_blood_donation.domain.Doacao;
import com.doeaqui.sboot_atom_blood_donation.model.DoacaoResponse;
import com.doeaqui.sboot_atom_blood_donation.model.NewDoacaoRequest;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DoacaoMapper {

    Doacao toDoacao(NewDoacaoRequest doacaoRequest);
    DoacaoResponse toDoacaoResponse(Doacao doacao);
    List<DoacaoResponse> toDoacaoResponseList(List<Doacao> doacaoList);

    default OffsetDateTime map(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atOffset(ZoneOffset.of("-03:00"));
    }

    default LocalDateTime map(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.toLocalDateTime();
    }
}