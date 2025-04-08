package com.map.config;

import com.map.dao.*;
import javax.sql.DataSource;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = "com.map")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/api/**")
	            .allowedOrigins("http://localhost:8080")  // Vue 서버 주소
	            .allowedMethods("*");
	}
	
	
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3308/testdb?serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("admin");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}

