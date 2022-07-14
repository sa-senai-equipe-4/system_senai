package br.com.senai.sa.service;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.senai.sa.entity.Usuario;
import br.com.senai.sa.exception.RegistroNaoEncontradoException;
import br.com.senai.sa.repository.UsuariosRepository;
import br.com.senai.sa.validation.AoAlterar;
import br.com.senai.sa.validation.AoInserir;

@Validated
@Service
public class UsuarioService {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Validated(AoInserir.class)
	public Usuario inserir(@Valid Usuario usuario) {
		return usuariosRepository.save(usuario);
	}
	
	@Validated(AoAlterar.class)
	public Usuario alterar(@Valid Usuario usuarioSalvo) {
		this.buscarPor(usuarioSalvo.getCodigo());
		return usuariosRepository.save(usuarioSalvo);
	}
	
	public void remover(@NotNull(message = "O código do cliente deve ser informado") Integer codigo) {
		this.buscarPor(codigo);
		this.usuariosRepository.deleteById(codigo);
	}
	
	public Usuario buscarPor(
			@NotEmpty(message = "O login deve ser informado") String login, 
			@NotEmpty(message = "A senha deve ser informada") String senha) {
		Optional<Usuario> usuarioEncontrado = usuariosRepository.buscarParaLoginPor(login, senha);
		
		if (usuarioEncontrado.isPresent()) {
			return usuarioEncontrado.get();
		}
		
		throw new RegistroNaoEncontradoException("O login ou a senha são inválidos");
	}
	
	public Usuario buscarPor(@NotNull(message = "O código do cliente deve ser informado") Integer codigo) {
		Optional<Usuario> usuarioEncontrado = usuariosRepository.buscarPor(codigo);
		
		if(usuarioEncontrado.isPresent()) {
			return usuarioEncontrado.get();
		}
		
		throw new RegistroNaoEncontradoException("Usuário não encontrado");
	}

}
