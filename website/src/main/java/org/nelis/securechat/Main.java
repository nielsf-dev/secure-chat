package org.nelis.securechat;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class Main {

    @Bean
    public DataSource getDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/chat");
        hikariDataSource.setUsername("postgres");
        hikariDataSource.setPassword("toor");
        return hikariDataSource;
    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
