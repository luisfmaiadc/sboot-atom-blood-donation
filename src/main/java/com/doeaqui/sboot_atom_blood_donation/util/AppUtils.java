package com.doeaqui.sboot_atom_blood_donation.util;

import com.doeaqui.sboot_atom_blood_donation.config.security.CustomUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;

@UtilityClass
public class AppUtils {

    public static void requireAtLeastOneNonNull(List<Object> paramsList) {
        if (paramsList.stream().allMatch(Objects::isNull)) throw new IllegalArgumentException("Algum parâmetro deve ser informado na requisição.");
    }

    public static CustomUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public static boolean isAdmin() {
        CustomUserDetails userDetails = getUserDetails();
        return userDetails.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN"));
    }
}