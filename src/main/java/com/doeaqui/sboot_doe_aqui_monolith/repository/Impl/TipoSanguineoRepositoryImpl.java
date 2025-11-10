package com.doeaqui.sboot_doe_aqui_monolith.repository.Impl;

import com.doeaqui.sboot_doe_aqui_monolith.domain.TipoSanguineo;
import com.doeaqui.sboot_doe_aqui_monolith.repository.TipoSanguineoRepository;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@UseClasspathSqlLocator
public interface TipoSanguineoRepositoryImpl extends TipoSanguineoRepository {

    @Override
    @SqlQuery
    @RegisterBeanMapper(TipoSanguineo.class)
    List<TipoSanguineo> getTiposSanguineos();
}