package com.sistema.examenes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing

public class SistemaExamenesBackendApplication  {



    public static void main(String[] args) {
        SpringApplication.run(SistemaExamenesBackendApplication.class, args);
    }



}
