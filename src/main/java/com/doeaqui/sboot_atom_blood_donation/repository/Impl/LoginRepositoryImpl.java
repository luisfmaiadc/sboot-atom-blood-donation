package com.doeaqui.sboot_atom_blood_donation.repository.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.Login;
import com.doeaqui.sboot_atom_blood_donation.repository.LoginRepository;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

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
}