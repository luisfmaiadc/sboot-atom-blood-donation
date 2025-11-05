package com.doeaqui.sboot_atom_blood_donation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private Character genero;
    private String telefone;
    private Byte idTipoSanguineo;
    private Boolean ativo;
}