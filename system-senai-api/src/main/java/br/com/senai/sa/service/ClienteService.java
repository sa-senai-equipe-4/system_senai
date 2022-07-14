package br.com.senai.sa.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.senai.sa.entity.Cliente;
import br.com.senai.sa.exception.RegistroNaoEncontradoException;
import br.com.senai.sa.repository.ClientesRepository;
import br.com.senai.sa.validation.AoAlterar;
import br.com.senai.sa.validation.AoInserir;

@Validated
@Service
public class ClienteService {
	
	@Autowired
	private ClientesRepository clientesRepository;
	
	@Validated(AoInserir.class)
	public Cliente inserir(@Valid Cliente cliente) {
		return clientesRepository.save(cliente);
	}
	
	@Validated(AoAlterar.class)
	public Cliente alterar(@Valid Cliente cliente) {
		this.buscarPor(cliente.getCodigo());
		return clientesRepository.save(cliente);
	}
	
	public void remover(@NotNull(message =  "O código do cliente deve ser informado") Integer codigo) {
		this.buscarPor(codigo);
		this.clientesRepository.deleteById(codigo);
	}
	
	public Cliente buscarPor(@NotNull(message =  "O código do cliente deve ser informado") Integer codigo) {
		Optional<Cliente> clienteEncontrado = clientesRepository.buscarPor(codigo);
		
		if(clienteEncontrado.isPresent()) {
			return clienteEncontrado.get();
		}
		
		throw new RegistroNaoEncontradoException("Cliente não encontrado");
	}
	
	public List<Cliente> buscarPor(
			@NotEmpty(message = "O parâmetro nome completo deve ser informado") String nomeCompleto) {
		return clientesRepository.listarPor("%"+nomeCompleto+"%");
	}
	

}
