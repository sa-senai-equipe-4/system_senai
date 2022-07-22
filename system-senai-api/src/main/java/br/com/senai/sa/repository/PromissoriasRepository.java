package br.com.senai.sa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.senai.sa.entity.Promissoria;

public interface PromissoriasRepository extends JpaRepository<Promissoria, Integer>{
	
	@Query(value = 
			"SELECT p "
			+ "FROM Promissoria p "
			+ "JOIN FETCH p.cliente c "
			+ "JOIN FETCH c.usuario "
			+ "WHERE p.codigo = :codigo ")
	Optional<Promissoria> buscarPor(Integer codigo);
	
	@Query(value = 
			"SELECT p "
			+ "FROM Promissoria p "
			+ "JOIN FETCH p.cliente c "
			+ "JOIN FETCH c.usuario "
			+ "WHERE Upper(p.cliente.nomeCompleto) LIKE Upper(:nomeCompleto) ")
	List<Promissoria> listarPor(String nomeCompleto);
	
	@Modifying
	@Query(value = 
			"UPDATE Promissoria p "
			+ "SET p.cliente.codigo = :codigoDoCliente "
			+ "WHERE p.codigo = :codigoDaPromissoria ")
	void alterar(Integer codigoDoCliente, Integer codigoDaPromissoria);

}
