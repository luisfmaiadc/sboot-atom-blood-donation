package com.doeaqui.sboot_atom_blood_donation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Hemocentro {

    private Integer id;
    private String nome;
    private String telefone;
    private String email;
}