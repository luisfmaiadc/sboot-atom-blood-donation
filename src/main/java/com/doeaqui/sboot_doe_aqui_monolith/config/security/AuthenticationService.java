package com.doeaqui.sboot_doe_aqui_monolith.config.security;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Login;
import com.doeaqui.sboot_doe_aqui_monolith.repository.LoginRepository;
import com.doeaqui.sboot_doe_aqui_monolith.service.PapelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final PapelService papelService;
    private final LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Login login = Optional.ofNullable(loginRepository.findByEmail(email))
                .orElseThrow(() -> new AuthorizationDeniedException("Usuário inexistente."));

        String role = papelService.getPapeisUsuarios().stream().
                filter(p -> p.getId().equals(login.getIdPapel()))
                .findFirst()
                .orElseThrow(() -> new AuthorizationDeniedException("Role informada é inválida."))
                .getNome();
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
        return new CustomUserDetails(login, authorities);
    }
}