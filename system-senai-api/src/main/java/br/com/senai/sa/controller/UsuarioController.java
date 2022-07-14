package br.com.senai.sa.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.sa.controller.converter.MapConverter;
import br.com.senai.sa.entity.Usuario;
import br.com.senai.sa.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MapConverter mapConverter;
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Usuario usuario){
		usuarioService.inserir(usuario);
		return ResponseEntity.created(URI.create("/codigo/" + usuario.getCodigo())).build();
	}
	
	@GetMapping("/codigo/{codigo}")
	public ResponseEntity<?> buscarPor(@PathVariable(name = "codigo") Integer codigo){
		return ResponseEntity.ok(mapConverter.toJsonMap(usuarioService.buscarPor(codigo)));
	}
	
	@PutMapping
	public ResponseEntity<?> alterar(@RequestBody Usuario usuario){
		return ResponseEntity.ok(mapConverter.toJsonMap(usuarioService.alterar(usuario)));
	}
	
	@DeleteMapping("/codigo/{codigo}")
	public ResponseEntity<?> removerPor(@PathVariable(name = "codigo") Integer codigo){
		this.usuarioService.remover(codigo);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> buscarPor(
			@RequestParam(name = "login") String login, @RequestParam(name = "senha") String senha){
		return ResponseEntity.ok(mapConverter.toJsonMap(usuarioService.buscarPor(login, senha)));
	}

}
