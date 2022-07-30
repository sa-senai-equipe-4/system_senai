package br.com.senai.sa;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InitApp {

	public static void main(String[] args) {
		SpringApplication.run(InitApp.class, args);
	}
	
	@Bean
	public Hibernate5Module jsonHibernate5Module() {
		return new Hibernate5Module();
	}
	
	@Bean	
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {			
		};
	}

}
