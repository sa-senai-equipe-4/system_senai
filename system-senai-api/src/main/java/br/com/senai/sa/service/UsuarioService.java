package br.com.senai.sa.service;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.senai.sa.entity.Usuario;
import br.com.senai.sa.exception.RegistroNaoEncontradoException;
import br.com.senai.sa.repository.UsuariosRepository;

@Validated
@Service
public class UsuarioService {
	
	@Autowired
	private UsuariosRepository usuariosRepository;
	
	public Usuario buscarPor(
			@NotEmpty(message = "O login deve ser informado") String login, 
			@NotEmpty(message = "A senha deve ser informada") String senha) {
		Optional<Usuario> usuarioEncontrado = usuariosRepository.buscarParaLoginPor(login, senha);
		
		if (usuarioEncontrado.isPresent()) {
			return usuarioEncontrado.get();
		}
		
		throw new RegistroNaoEncontradoException("O login ou a senha são inválidos");
	}

}
