package com.doeaqui.sboot_doe_aqui_monolith.repository.Impl;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Login;
import com.doeaqui.sboot_doe_aqui_monolith.repository.LoginRepository;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@UseClasspathSqlLocator
public interface LoginRepositoryImpl extends LoginRepository {

    @Override
    @SqlUpdate
    void postNewLogin(@BindBean Login login);

    @Override
    @SqlQuery
    @RegisterBeanMapper(Login.class)
    Login findByEmail(@Bind("email") String email);

    @Override
    @SqlQuery
    @RegisterBeanMapper(Login.class)
    Optional<Login> getLoginInfoById(@Bind("idUsuario") Integer idUsuario);

    @Override
    @SqlUpdate
    void patchLoginEmailOrPapel(@BindBean Login login);

    @Override
    @SqlUpdate
    void patchLoginSenha(@BindBean Login login);
}