package com.doeaqui.sboot_doe_aqui_monolith.repository.Impl;

import com.doeaqui.sboot_doe_aqui_monolith.domain.Papel;
import com.doeaqui.sboot_doe_aqui_monolith.repository.PapelRepository;
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