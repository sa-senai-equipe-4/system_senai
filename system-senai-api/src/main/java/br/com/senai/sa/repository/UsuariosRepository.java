package br.com.senai.sa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.senai.sa.entity.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer>{
	
	@Query(value = 
			"SELECT u "
			+ "FROM Usuario u "
			+ "WHERE u.login = :login "
			+ "AND u.senha = :senha ")
	Optional<Usuario> buscarParaLoginPor(String login, String senha);
	
	@Query(value = 
			"SELECT u "
			+ "FROM Usuario u "
			+ "WHERE u.codigo = :codigo ")
	Optional<Usuario> buscarPor(Integer codigo);

}
