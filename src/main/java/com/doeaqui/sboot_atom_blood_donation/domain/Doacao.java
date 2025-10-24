package com.doeaqui.sboot_atom_blood_donation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Doacao {

    private Integer id;
    private Integer idUsuario;
    private Integer idSolicitacaoDoacao;
    private Integer idHemocentro;
    private LocalDateTime dataDoacao;
    private Integer volume;
    private String observacoes;
}