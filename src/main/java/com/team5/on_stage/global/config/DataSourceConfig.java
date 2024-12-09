package com.team5.on_stage.global.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.team5.on_stage",
        entityManagerFactoryRef = "serviceEntityManager",
        transactionManagerRef = "serviceTransactionManager"
)
public class DataSourceConfig {

    @Primary
    @Bean(name = "batchDataSource")
    @ConfigurationProperties(prefix = "batch.datasource")
    public DataSource batchDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "serviceDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource serviceDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "serviceEntityManager")
    public LocalContainerEntityManagerFactoryBean serviceEntityManager(
            @Qualifier("serviceDataSource") DataSource serviceDataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(serviceDataSource);
        entityManagerFactory.setPackagesToScan("com.team5.on_stage");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Hibernate 설정
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.enable_lazy_load_no_trans", "true");

        entityManagerFactory.setJpaProperties(properties);

        return entityManagerFactory;
    }

    @Primary
    @Bean(name = "batchTransactionManager")
    public PlatformTransactionManager batchTransactionManager(
            @Qualifier("batchDataSource") DataSource batchDataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(batchDataSource);
        return transactionManager;
    }

    @Bean(name = "serviceTransactionManager")
    public PlatformTransactionManager serviceTransactionManager(
            @Qualifier("serviceEntityManager") LocalContainerEntityManagerFactoryBean serviceEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(serviceEntityManager.getObject());
        return transactionManager;
    }
}

