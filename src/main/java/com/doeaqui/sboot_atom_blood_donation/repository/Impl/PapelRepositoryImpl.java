package com.doeaqui.sboot_atom_blood_donation.repository.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.Papel;
import com.doeaqui.sboot_atom_blood_donation.repository.PapelRepository;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@UseClasspathSqlLocator
public interface PapelRepositoryImpl extends PapelRepository {

    @Override
    @SqlQuery
    @RegisterBeanMapper(Papel.class)
    List<Papel> getPapeisUsuarios();
}