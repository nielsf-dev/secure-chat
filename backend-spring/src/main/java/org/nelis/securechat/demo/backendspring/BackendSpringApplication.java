package org.nelis.securechat.demo.backendspring;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@SpringBootApplication
@EntityScan("org.nelis.securechat.domain")
@EnableJpaRepositories
public class BackendSpringApplication {

    @Bean
    public DataSource getDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1:5432/chat");
        hikariDataSource.setUsername("postgres");
        hikariDataSource.setPassword("toor");
        return hikariDataSource;
    }

	public static void main(String[] args) {
		SpringApplication.run(BackendSpringApplication.class, args);
	}
}
