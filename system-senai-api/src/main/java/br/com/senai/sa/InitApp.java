package br.com.senai.sa;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import br.com.senai.sa.entity.Usuario;
import br.com.senai.sa.entity.enums.Perfil;
import br.com.senai.sa.exception.RegistroNaoEncontradoException;
import br.com.senai.sa.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InitApp {
	
	@Autowired
	private UsuarioService usuarioService;

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
			try {
				usuarioService.buscarPor("admin", "admin");
			} catch (RegistroNaoEncontradoException e) {
				Usuario admin = new Usuario();
				admin.setLogin("admin");
				admin.setSenha("admin");
				admin.setPerfil(Perfil.GESTOR);
				usuarioService.inserirAdmin(admin);
			}
			
		};
	}

}
