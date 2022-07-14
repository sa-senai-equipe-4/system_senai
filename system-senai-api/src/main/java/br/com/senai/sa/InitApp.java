package br.com.senai.sa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import br.com.senai.sa.entity.Usuario;
import br.com.senai.sa.service.UsuarioService;

@SpringBootApplication
public class InitApp {

	public static void main(String[] args) {
		SpringApplication.run(InitApp.class, args);
	}
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Bean	
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {			
//			Usuario usuarioLogado = usuarioService.buscarPor("gustavo.senai", "123456");
//			System.out.println(usuarioLogado.getPerfil());
		};
	}

}
