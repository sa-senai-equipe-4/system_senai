package br.com.senai.sa.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.google.common.base.Preconditions;

import br.com.senai.sa.entity.Cliente;
import br.com.senai.sa.entity.Promissoria;
import br.com.senai.sa.exception.RegistroNaoEncontradoException;
import br.com.senai.sa.repository.ClientesRepository;
import br.com.senai.sa.repository.PromissoriasRepository;
import br.com.senai.sa.validation.AoAlterar;
import br.com.senai.sa.validation.AoInserir;

@Validated
@Service
public class ClienteService {
	
	@Autowired
	private ClientesRepository clientesRepository;
	
	@Autowired
	private PromissoriasRepository promissoriasRepository;
	
	private static final String PATTERN = 
			"((?=.*\\d)(?=.*[a-zA-Z]).{2,})";
	
	@Validated(AoInserir.class)
	public Cliente inserir(@Valid Cliente cliente) {
		Preconditions.checkArgument(validaSenha(cliente.getUsuario().getSenha()), "A senha deve conter letras e números");
		return clientesRepository.save(cliente);
	}
	
	@Validated(AoAlterar.class)
	public Cliente alterar(@Valid Cliente cliente) {
		this.buscarPor(cliente.getCodigo());
		Preconditions.checkArgument(validaSenha(cliente.getUsuario().getSenha()), "A senha deve conter letras e números");
		return clientesRepository.save(cliente);
	}
	
	public void remover(@NotNull(message =  "O código do cliente deve ser informado") Integer codigo) {
		this.buscarPor(codigo);
		List<Promissoria> promissoriasDoCliente = promissoriasRepository.listarPor(codigo);
		Preconditions.checkArgument(promissoriasDoCliente.size() == 0, "Não é possível remover clientes com promisssória gerada");
		this.clientesRepository.deleteById(codigo);
	}
	
	public Cliente buscarPor(@NotNull(message =  "O código do cliente deve ser informado") Integer codigo) {
		Optional<Cliente> clienteEncontrado = clientesRepository.buscarPor(codigo);
		
		if(clienteEncontrado.isPresent()) {
			return clienteEncontrado.get();
		}
		
		throw new RegistroNaoEncontradoException("Cliente não encontrado");
	}
	
	private boolean validaSenha(String senha) {
		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(senha);
		if(matcher.matches()) {
			return true;
		}
		return false;
	}
	
	public List<Cliente> buscarPor(
			@NotEmpty(message = "O parâmetro nome completo deve ser informado") String nomeCompleto) {
		return clientesRepository.listarPor("%"+nomeCompleto+"%");
	}

	public Cliente buscarPorCodigoDeUsuario(@NotNull(message =  "O código do usuário deve ser informado") Integer codigoDoUsuario) {
		
		Optional<Cliente> clienteEncontrado = clientesRepository.buscarPorUsuarioCom(codigoDoUsuario);
		
		if(clienteEncontrado.isPresent()) {
			return clienteEncontrado.get();
		}
		
		throw new RegistroNaoEncontradoException("Cliente não encontrado");
	}

	public List<Cliente> listarTodos() {
		return clientesRepository.listarTodos();
	}
	

}
