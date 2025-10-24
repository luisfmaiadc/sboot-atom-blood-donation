package com.doeaqui.sboot_atom_blood_donation.repository.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.Usuario;
import com.doeaqui.sboot_atom_blood_donation.repository.UsuarioRepository;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

@Repository
@UseClasspathSqlLocator
public interface UsuarioRepositoryImpl extends UsuarioRepository {

    @SqlUpdate
    @GetGeneratedKeys
    int postNewUser(@BindBean Usuario usuario);
}