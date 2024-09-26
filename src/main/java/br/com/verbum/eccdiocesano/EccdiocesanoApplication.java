package br.com.verbum.eccdiocesano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

@SpringBootApplication(exclude = FlywayAutoConfiguration.class)
public class EccdiocesanoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EccdiocesanoApplication.class, args);
    }

}
