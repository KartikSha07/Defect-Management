package com.cognizant.main;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@ComponentScan("com.cognizant")
@SpringBootApplication(scanBasePackages="com.cognizant.*")
@EntityScan(basePackages="com.cognizant.entities")
@EnableJpaRepositories(basePackages="com.cognizant.repositries")
@EnableAspectJAutoProxy
public class DefectsManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DefectsManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataInitializer(DataSource dataSource) {
        return args -> {
            ScriptUtils.executeSqlScript(dataSource.getConnection(),
                    new ClassPathResource("data.sql"));
        };
    }
}
