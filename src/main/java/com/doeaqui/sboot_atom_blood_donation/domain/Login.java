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
public class Login {

    private Integer idUsuario;
    private String email;
    private String senha;
    private LocalDateTime ultimoLogin;
    private Byte tentativasFalhas;
}