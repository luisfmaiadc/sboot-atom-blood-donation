package com.doeaqui.sboot_doe_aqui_monolith.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoDoacao {

    private Integer id;
    private Integer idUsuario;
    private Integer idHemocentro;
    private Byte idTipoSanguineo;
    private LocalDateTime dataSolicitacao;
    private Status status;
    private LocalDateTime dataEncerramento;
    private String observacoes;
}