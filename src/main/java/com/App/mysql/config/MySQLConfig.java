package com.App.mysql.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "entityManagerFactoryMySQL",
        transactionManagerRef = "transactionManagerMySQL",
        basePackages = "com.App.mysql.repositories"
)
public class MySQLConfig {

    @Primary
    @ConfigurationProperties(prefix = "db2.datasource")
    @Bean("dataSourceMySQL")
    public DataSource createMysqlDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean("entityManagerFactoryMySQL")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder){
        System.out.println("...");
        Map<String,Object> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect");
        hibernateProperties.put("hibernate.hbm2ddl.auto","update");
        hibernateProperties.put("hibernate.show_sql","true");
        return entityManagerFactoryBuilder
                .dataSource(createMysqlDataSource())
                .properties(hibernateProperties)
                .packages("com.App.mysql.entities")
                .build();
    }
//@Qualifier("entityManagerFactoryMySQL")

    @Primary
    @Bean("transactionManagerMySQL")
    public PlatformTransactionManager createTransactionManager(@Qualifier("entityManagerFactoryMySQL") EntityManagerFactory entityManagerFactory){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
}
