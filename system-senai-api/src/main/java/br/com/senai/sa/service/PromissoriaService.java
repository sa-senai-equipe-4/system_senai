package br.com.senai.sa.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.com.senai.sa.entity.Promissoria;
import br.com.senai.sa.exception.RegistroNaoEncontradoException;
import br.com.senai.sa.repository.PromissoriasRepository;
import br.com.senai.sa.validation.AoAlterar;
import br.com.senai.sa.validation.AoInserir;

@Validated
@Service
public class PromissoriaService {
	
	@Autowired
	private PromissoriasRepository promissoriasRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Transactional
	@Validated(AoInserir.class)
	public Promissoria inserir(@Valid Promissoria promissoria) {
		this.clienteService.buscarPor(promissoria.getCliente().getCodigo());
		return promissoriasRepository.save(promissoria);
	}
	
	@Transactional
	@Validated(AoAlterar.class)
	public Promissoria alterar(@Valid Promissoria promissoriaSalvo) {
		this.buscarPor(promissoriaSalvo.getCodigo());
		return promissoriasRepository.save(promissoriaSalvo);
	}
	
	public void remover(@NotNull(message = "O código da promissória deve ser informado") Integer codigo) {
		this.buscarPor(codigo);
		this.promissoriasRepository.deleteById(codigo);
	}
	
	public Promissoria buscarPor(@NotNull(message = "O código da promissória deve ser informado") Integer codigo) {
		Optional<Promissoria> promissoriaEncontrada = promissoriasRepository.buscarPor(codigo);
		
		if(promissoriaEncontrada.isPresent()) {
			return promissoriaEncontrada.get();
		}
		
		throw new RegistroNaoEncontradoException("Promissória não encontrada");
	}
	
	public List<Promissoria> buscarPor(
			@NotEmpty(message = "O parâmetro do nome completo do cliente deve ser informado") String nomeCompleto) {
		return promissoriasRepository.listarPor("%"+nomeCompleto+"%");
	}
	

}
