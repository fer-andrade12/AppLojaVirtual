package com.App.LojaVirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "com.App.LojaVirtual.model")
@ComponentScan(basePackages = {"com.*"})
@EnableJpaRepositories(basePackages = {"com.App.LojaVirtual.repository"})
@EnableTransactionManagement
public class LojaVirtualApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualApplication.class, args);
		
		// senha criptografada atualmente salva no banco usuario 
		// $2a$10$buSqJVxJaSDEav9NenMw1OOrwNLptLXU67bIZm4uxQILKyTef5n/a
		//System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
	
 }
