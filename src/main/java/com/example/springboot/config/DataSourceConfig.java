package com.example.springboot.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean
    public HikariDataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://x3ztd854gaa7on6s.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/rhf8wjsjb6dul6vh");
        ds.setUsername("x8bfxhwighufv5r0");
        ds.setPassword("e89gov09v1v9nl6x");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return ds;
    }
}
