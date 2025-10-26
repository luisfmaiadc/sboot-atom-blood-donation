package com.doeaqui.sboot_atom_blood_donation.config.security;

import com.doeaqui.sboot_atom_blood_donation.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final LoginRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = repository.findByEmail(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }
        return userDetails;
    }
}