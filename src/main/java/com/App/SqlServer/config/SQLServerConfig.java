package com.App.SqlServer.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySQLServer",
        transactionManagerRef = "transactionManagerSQLServer",
        basePackages = "com.App.SqlServer.repositories"
)
public class SQLServerConfig {

    @ConfigurationProperties(prefix = "db1.datasource")
    @Bean("dataSourceSQLServer")
    public DataSource createSqlServerDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("entityManagerFactorySQLServer")
    public LocalContainerEntityManagerFactoryBean sqlServerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder){
        Map<String,Object> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.dialect","org.hibernate.dialect.SQLServerDialect");
        hibernateProperties.put("hibernate.hbm2ddl.auto","update");
        hibernateProperties.put("hibernate.show_sql","true");
        return entityManagerFactoryBuilder
                .dataSource(createSqlServerDataSource())
                .properties(hibernateProperties)
                .packages("com.App.SqlServer.entities")
                .build();
    }
//@Qualifier("entityManagerFactorySQLServer")
    @Bean("transactionManagerSQLServer")
    public PlatformTransactionManager createPlatformTransactionManager(@Qualifier("entityManagerFactorySQLServer") EntityManagerFactory entityManagerFactory){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
}
