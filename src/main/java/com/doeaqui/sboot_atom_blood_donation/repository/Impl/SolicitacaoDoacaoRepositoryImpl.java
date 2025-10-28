package com.doeaqui.sboot_atom_blood_donation.repository.Impl;

import com.doeaqui.sboot_atom_blood_donation.domain.SolicitacaoDoacao;
import com.doeaqui.sboot_atom_blood_donation.repository.SolicitacaoRepository;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
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
public interface SolicitacaoDoacaoRepositoryImpl extends SolicitacaoRepository {

    @Override
    @SqlUpdate
    @GetGeneratedKeys
    int postNewSolicitacaoDoacao(@BindBean SolicitacaoDoacao solicitacaoDoacao);

    @Override
    @SqlQuery
    boolean isSolicitacaoDoacaoValid(@Bind("idUsuario") Integer idUsuario);

    @Override
    @SqlQuery
    @RegisterBeanMapper(SolicitacaoDoacao.class)
    List<SolicitacaoDoacao> getSolicitacaoDoacaoByFilter(@Bind("idUsuario")Integer idUsuario, @Bind("idHemocentro") Integer idHemocentro, @Bind("idTipoSanguineo") Integer idTipoSanguineo,
                                                         @Bind("dataSolicitacao") LocalDate dataSolicitacao, @Bind("status") String status, @Bind("dataEncerramento") LocalDate dataEncerramento);

    @Override
    @SqlQuery
    @RegisterBeanMapper(SolicitacaoDoacao.class)
    Optional<SolicitacaoDoacao> getSolicitacaoDoacaoInfoById(@Bind("idSolicitacaoDoacao") Integer idSolicitacaoDoacao);
}