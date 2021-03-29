package com.multidb.adapters.persistence.mysql.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.multidb.adapters.persistence.mysql.repositories",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
public class MysqlDataSource {

    @Primary
    @Bean(name = "mysqlDataSourceProperties")
    @ConfigurationProperties("spring.mysql-datasource")
    public DataSourceProperties mysqlDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "mysqlHikariDataSource")
    @ConfigurationProperties("spring.mysql-datasource.hikari")
    public DataSource mysqlDataSource(@Qualifier("mysqlDataSourceProperties") DataSourceProperties mysqlDataSourceProperties) {
        return mysqlDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder mysqlEntityManagerFactoryBuilder, @Qualifier("mysqlHikariDataSource") DataSource mysqlDataSource) {

        Map<String, String> mysqlJpaProperties = new HashMap<>();
        mysqlJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        mysqlJpaProperties.put("hibernate.hbm2ddl.auto", "create");

        return mysqlEntityManagerFactoryBuilder
                .dataSource(mysqlDataSource)
                .packages("com.multidb.adapters.persistence.mysql.entities")
                .persistenceUnit("mysqlDataSource")
                .properties(mysqlJpaProperties)
                .build();
    }

    @Primary
    @Bean(name = "mysqlTransactionManager")
    public PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory) {

        return new JpaTransactionManager(mysqlEntityManagerFactory);
    }
}
