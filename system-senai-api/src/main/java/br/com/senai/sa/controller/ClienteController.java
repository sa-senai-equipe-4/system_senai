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
import br.com.senai.sa.entity.Cliente;
import br.com.senai.sa.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private MapConverter mapConverter;
	
	@PostMapping
	public ResponseEntity<?> inserir(@RequestBody Cliente cliente){
		clienteService.inserir(cliente);
		return ResponseEntity.created(URI.create("/codigo/" + cliente.getCodigo())).build();
	}
	
	@GetMapping("/codigo/{codigo}")
	public ResponseEntity<?> buscarPor(@PathVariable(name = "codigo") Integer codigo){
		return ResponseEntity.ok(mapConverter.toJsonMap(clienteService.buscarPor(codigo)));
	}
	
	@GetMapping("/codigo-usuario/{codigo-usuario}")
	public ResponseEntity<?> buscarUsuarioPor(@PathVariable(name = "codigo-usuario") Integer codigoDoUsuario){
		return ResponseEntity.ok(mapConverter.toJsonMap(clienteService.buscarPorCodigoDeUsuario(codigoDoUsuario)));
	}
	
	@PutMapping
	public ResponseEntity<?> alterar(@RequestBody Cliente cliente){
		return ResponseEntity.ok(mapConverter.toJsonMap(clienteService.alterar(cliente)));
	}
	
	@DeleteMapping("/codigo/{codigo}")
	public ResponseEntity<?> removerPor(@PathVariable(name = "codigo") Integer codigo){
		this.clienteService.remover(codigo);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<?> buscarPor(@RequestParam(name = "nome-completo") String nomeCompleto){
		return ResponseEntity.ok(mapConverter.toJsonList(clienteService.buscarPor(nomeCompleto)));
	}
	
	@GetMapping("/listar-todos")
	public ResponseEntity<?> listarTodos(){
		return ResponseEntity.ok(mapConverter.toJsonList(clienteService.listarTodos()));
	}

}
