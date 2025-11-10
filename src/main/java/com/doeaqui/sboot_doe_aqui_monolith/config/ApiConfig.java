package com.doeaqui.sboot_doe_aqui_monolith.config;

import com.doeaqui.sboot_doe_aqui_monolith.repository.Impl.*;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
@EnableCaching
public class ApiConfig {

    @Bean
    public Jdbi jdbi(DataSource dataSource) {
        TransactionAwareDataSourceProxy dataSourceProxy = new TransactionAwareDataSourceProxy(dataSource);
        Jdbi jdbi = Jdbi.create(dataSourceProxy);
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Bean
    public UsuarioRepositoryImpl usuarioRepository(Jdbi jdbi) {
        return jdbi.onDemand(UsuarioRepositoryImpl.class);
    }

    @Bean
    public LoginRepositoryImpl loginRepository(Jdbi jdbi) {
        return jdbi.onDemand(LoginRepositoryImpl.class);
    }

    @Bean
    public TipoSanguineoRepositoryImpl tipoSanguineoRepository(Jdbi jdbi) {
        return jdbi.onDemand(TipoSanguineoRepositoryImpl.class);
    }

    @Bean
    public PapelRepositoryImpl papelRepository(Jdbi jdbi) {
        return jdbi.onDemand(PapelRepositoryImpl.class);
    }

    @Bean
    public HemocentroRepositoryImpl hemocentroRepository(Jdbi jdbi) {
        return jdbi.onDemand(HemocentroRepositoryImpl.class);
    }

    @Bean
    public SolicitacaoDoacaoRepositoryImpl solicitacaoDoacaoRepository(Jdbi jdbi) {
        return jdbi.onDemand(SolicitacaoDoacaoRepositoryImpl.class);
    }

    @Bean
    public DoacaoRepositoryImpl doacaoRepository(Jdbi jdbi) {
        return jdbi.onDemand(DoacaoRepositoryImpl.class);
    }
}