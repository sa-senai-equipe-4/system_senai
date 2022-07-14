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
import br.com.senai.sa.entity.Promissoria;
import br.com.senai.sa.service.PromissoriaService;

@RestController
@RequestMapping("/promissorias")
public class PromissoriaController {
	
	@Autowired
	private PromissoriaService promissoriaService;
	
	@Autowired
	private MapConverter mapConverter;
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Promissoria promissoria){
		promissoriaService.inserir(promissoria);
		return ResponseEntity.created(URI.create("/codigo/" + promissoria.getCodigo())).build();
	}
	
	@GetMapping("/codigo/{codigo}")
	public ResponseEntity<?> buscarPor(@PathVariable(name = "codigo") Integer codigo){
		return ResponseEntity.ok(mapConverter.toJsonMap(promissoriaService.buscarPor(codigo)));
	}
	
	@PutMapping
	public ResponseEntity<?> alterar(@RequestBody Promissoria promissoria){
		return ResponseEntity.ok(mapConverter.toJsonMap(promissoriaService.alterar(promissoria)));
	}
	
	@DeleteMapping("/codigo/{codigo}")
	public ResponseEntity<?> removerPor(@PathVariable(name = "codigo") Integer codigo){
		this.promissoriaService.remover(codigo);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<?> buscarPor(@RequestParam(name = "nome-completo") String nomeCompleto){
		return ResponseEntity.ok(mapConverter.toJsonList(promissoriaService.buscarPor(nomeCompleto)));
	}

}
