package com.doeaqui.sboot_doe_aqui_monolith.repository.Impl;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Usuario;
import com.doeaqui.sboot_doe_aqui_monolith.model.UsuarioResponse;
import com.doeaqui.sboot_doe_aqui_monolith.repository.UsuarioRepository;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@UseClasspathSqlLocator
public interface UsuarioRepositoryImpl extends UsuarioRepository {

    @Override
    @SqlUpdate
    @GetGeneratedKeys
    int postNewUser(@BindBean Usuario usuario);

    @Override
    @SqlQuery
    @RegisterBeanMapper(UsuarioResponse.class)
    Optional<UsuarioResponse> getUserInfoById(@Bind("id") Integer id);

    @Override
    @SqlUpdate
    void patchUserInfo(@BindBean Usuario usuario);

    @Override
    @SqlUpdate
    void deleteUser(@Bind("id") Integer idUsuario);
}