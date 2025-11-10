package com.doeaqui.sboot_doe_aqui_monolith.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TipoSanguineo {

    private Byte id;
    private String tipo;
    private Character fator;
}