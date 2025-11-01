package com.doeaqui.sboot_atom_blood_donation.repository.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.Hemocentro;
import com.doeaqui.sboot_atom_blood_donation.repository.HemocentroRepository;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@UseClasspathSqlLocator
public interface HemocentroRepositoryImpl extends HemocentroRepository {

    @Override
    @SqlUpdate
    @GetGeneratedKeys
    int postNewHemocentro(@BindBean Hemocentro newHemocentro);

    @Override
    @SqlQuery
    @RegisterBeanMapper(Hemocentro.class)
    Optional<Hemocentro> getHemocentroInfoById(@Bind("idHemocentro") Integer idHemocentro);

    @Override
    @SqlQuery
    @RegisterBeanMapper(Hemocentro.class)
    List<Hemocentro> getHemocentroByFilter(@Bind("nome") String nome, @Bind("telefone") String telefone, @Bind("email") String email);

    @Override
    @SqlUpdate
    void patchHemocentroInfo(@BindBean Hemocentro hemocentro);
}