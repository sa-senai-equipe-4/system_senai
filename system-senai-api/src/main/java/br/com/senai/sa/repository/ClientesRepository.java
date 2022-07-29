package br.com.senai.sa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.senai.sa.entity.Cliente;

public interface ClientesRepository extends JpaRepository<Cliente, Integer>{
	
	@Query(value = 
			"SELECT c "
			+ "FROM Cliente c "
			+ "JOIN FETCH c.usuario "
			+ "WHERE c.codigo = :codigo ")
	Optional<Cliente> buscarPor(Integer codigo);
	
	@Query(value = 
			"SELECT c "
			+ "FROM Cliente c "
			+ "JOIN FETCH c.usuario "
			+ "WHERE Upper(c.nomeCompleto) LIKE Upper(:nomeCompleto) ")
	List<Cliente> listarPor(String nomeCompleto);

	@Query(value = 
			"SELECT c "
			+ "FROM Cliente c "
			+ "JOIN FETCH c.usuario u "
			+ "WHERE u.codigo = :codigoDoUsuario ")
	Optional<Cliente> buscarPorUsuarioCom(Integer codigoDoUsuario);

	@Query(value = 
			"SELECT c "
			+ "FROM Cliente c "
			+ "JOIN FETCH c.usuario ")
	List<Cliente> listarTodos();

}
