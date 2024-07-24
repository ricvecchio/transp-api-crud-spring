package com.transportadora.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJpaRepositories(
        basePackages = "com.tranposrtadora.repository.auth",
        entityManagerFactoryRef = "pedidosEntityManager",
        transactionManagerRef = "pedidosTransactionManager"
)

@Configuration
public class AuthDbConfig {

//    @Bean(name = "pedidosDataSource")
    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSourceProperties pedidosDataSource() {
        return new DataSourceProperties();
    }

//    @Bean(name = "pedidosEntityManager")
    @Bean
    public LocalContainerEntityManagerFactoryBean pedidosEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(pedidosDataSource().initializeDataSourceBuilder().build());
        em.setPackagesToScan("com.transportadora.model.auth");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean
    public PlatformTransactionManager pedidosTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(pedidosEntityManager().getObject());

        return transactionManager;
    }
}