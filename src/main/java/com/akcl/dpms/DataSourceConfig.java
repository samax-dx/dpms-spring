package com.akcl.dpms;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {
    @Primary
    @Bean(name = "dpms_ds")
    @ConfigurationProperties(prefix="spring.dpms.datasource")
    public DataSource dpmsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "base_ds")
    @ConfigurationProperties(prefix="spring.base.datasource")
    public DataSource originSource() {
        return DataSourceBuilder.create().build();
    }
}
