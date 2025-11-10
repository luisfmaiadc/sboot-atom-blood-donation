package com.doeaqui.sboot_atom_blood_donation.repository.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.Doacao;
import com.doeaqui.sboot_atom_blood_donation.repository.DoacaoRepository;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.AllowUnusedBindings;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@UseClasspathSqlLocator
public interface DoacaoRepositoryImpl extends DoacaoRepository {

    @Override
    @SqlUpdate
    @GetGeneratedKeys
    @AllowUnusedBindings
    int postNewDoacao(@BindBean Doacao doacao);

    @Override
    @SqlQuery
    @RegisterBeanMapper(Doacao.class)
    Optional<Doacao> getUltimaDoacao(@Bind("idUsuario") Integer idUsuario);

    @Override
    @SqlQuery
    @RegisterBeanMapper(Doacao.class)
    Optional<Doacao> getDoacaoInfoById(@Bind("idDoacao") Integer idDoacao);

    @Override
    @SqlQuery
    @RegisterBeanMapper(Doacao.class)
    List<Doacao> getDoacaoByFilter(@Bind("idHemocentro") Integer idHemocentro, @Bind("dataDoacao") LocalDate dataDoacao,
                                   @Bind("volume") Integer volume, @Bind("idUsuario") Integer idUsuario);
}