package com.doeaqui.sboot_atom_blood_donation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    private Integer idUsuario;
    private String email;
    private String senha;
    private Byte idPapel;
    private Byte tentativasFalhas;
}