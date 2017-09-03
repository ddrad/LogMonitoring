package com.dmitry.asset.control.logmonitoring.web.configure;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Dmitry Azarov 31.08.2017
 */

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
public class HibernateConfiguration {

    @Autowired
    private Environment environment;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String hibernateDialect;
    private String hibernateShowSql;
    private String hibernateHbm2ddlAuto;
    private String hibernateFormatSql;

    private String[] entityPackagesToScan = {
            "com.dmitry.asset.control.logmonitoring.*.datalayer.entity",
            "com.dmitry.asset.control.logmonitoring.*.*.datalayer.entity"};

    @PostConstruct
    public void init() {
        driverClassName = environment.getRequiredProperty("jdbc.driverClassName");
        url = environment.getRequiredProperty("jdbc.url");
        username = environment.getRequiredProperty("jdbc.username");
        password = environment.getRequiredProperty("jdbc.password");
        hibernateDialect = environment.getRequiredProperty("hibernate.dialect");
        hibernateShowSql = environment.getRequiredProperty("hibernate.show_sql");
        hibernateHbm2ddlAuto = environment.getRequiredProperty("hibernate.hbm2ddl.auto");
        hibernateFormatSql = environment.getRequiredProperty("hibernate.format_sql");
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(entityPackagesToScan);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", hibernateShowSql);
        properties.put("hibernate.format_sql", hibernateFormatSql);
        return properties;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }
}
