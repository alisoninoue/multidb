package com.multidb.adapters.persistence.sybase.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.multidb.adapters.persistence.sybase.repositories",
        entityManagerFactoryRef = "sybaseEntityManagerFactory",
        transactionManagerRef = "sybaseTransactionManager"
)
public class SybaseDataSource {

    @Bean(name = "sybaseDataSourceProperties")
    @ConfigurationProperties("spring.sybase-datasource")
    public DataSourceProperties sybaseDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "sybaseHikariDataSource")
    @ConfigurationProperties("spring.sybase-datasource.hikari")
    public DataSource sybaseDataSource(@Qualifier("sybaseDataSourceProperties") DataSourceProperties sybaseDataSourceProperties) {
        return sybaseDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    @Profile(value = "local")
    public DataSourceInitializer sybaseDataSourceInitializer(@Qualifier("sybaseHikariDataSource") DataSource datasource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("data-sybase.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(datasource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Bean(name = "sybaseEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sybaseEntityManagerFactory(
            EntityManagerFactoryBuilder sybaseEntityManagerFactoryBuilder, @Qualifier("sybaseHikariDataSource") DataSource sybaseDataSource) {

        Map<String, String> sybaseJpaProperties = new HashMap<>();
        sybaseJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.Sybase11Dialect");
        sybaseJpaProperties.put("hibernate.hbm2ddl.auto", "create");

        return sybaseEntityManagerFactoryBuilder
                .dataSource(sybaseDataSource)
                .packages("com.multidb.adapters.persistence.sybase.entities")
                .persistenceUnit("sybaseDataSource")
                .properties(sybaseJpaProperties)
                .build();
    }

    @Bean(name = "sybaseTransactionManager")
    public PlatformTransactionManager sybaseTransactionManager(
            @Qualifier("sybaseEntityManagerFactory") EntityManagerFactory sybaseEntityManagerFactory) {

        return new JpaTransactionManager(sybaseEntityManagerFactory);
    }
}
