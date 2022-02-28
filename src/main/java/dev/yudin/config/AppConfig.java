package dev.yudin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "dev.yudin")
@PropertySource("classpath:sqlite.properties")
public class AppConfig {

    @Autowired
    private Environment propertyDataHolder;

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        String jdbcDriver = propertyDataHolder.getProperty("jdbc.driver");
        String jdbcUrl = propertyDataHolder.getProperty("jdbc.url");

        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUrl(jdbcUrl);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());

        return jdbcTemplate;
    }
}

