package com.doeaqui.sboot_atom_blood_donation.util;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;

@UtilityClass
public class AppUtils {

    public static void requireAtLeastOneNonNull(List<Object> paramsList) {
        if (paramsList.stream().allMatch(Objects::isNull)) throw new IllegalArgumentException("Algum parâmetro deve ser informado na requisição.");
    }
}