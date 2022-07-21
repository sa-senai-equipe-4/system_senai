package br.com.senai.sa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.sa.controller.converter.MapConverter;
import br.com.senai.sa.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MapConverter mapConverter;
	
	@GetMapping("/login")
	public ResponseEntity<?> buscarPor(
			@RequestParam(name = "login") String login, @RequestParam(name = "senha") String senha){
		return ResponseEntity.ok(mapConverter.toJsonMap(usuarioService.buscarPor(login, senha)));
	}

}
