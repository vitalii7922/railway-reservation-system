/*
package com.tsystems.project.config;


import com.mysql.cj.jdbc.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class SpringConfig {
    @Bean
    public DataSource dataSource() throws SQLException {
        SimpleDriverDataSource db = new SimpleDriverDataSource();
        db.setUsername("root");
        db.setPassword("000");
        db.setDriver(new Driver());
        db.setUrl("jdbc:mysql://localhost:3306/railways?serverTimezone=UTC");
        return db;
    }

    @Bean
    public PlatformTransactionManager txManager() throws SQLException {
        return new DataSourceTransactionManager(dataSource());
    }


}
*/
