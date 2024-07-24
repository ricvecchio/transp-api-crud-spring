package com.transportadora.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJpaRepositories(
        basePackages = "com.transportadora.repository.app",
        entityManagerFactoryRef = "clientesEntityManager",
        transactionManagerRef = "clientesTransactionManager"
)
@Configuration
public class AppDbConfig {

    @Primary
    @Bean
    public DataSourceProperties clienteDataSource() {
        return new DataSourceProperties();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean clientesEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(clienteDataSource().initializeDataSourceBuilder().build());
        em.setPackagesToScan("com.transportadora.model.app");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager clientesTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(clientesEntityManager().getObject());

        return transactionManager;
    }
}
