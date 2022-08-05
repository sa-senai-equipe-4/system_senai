package br.com.senai.sa.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.senai.sa.dto.Cliente;

@Component
public class ClienteClient {
	
	@Value("${endpoint}")
	private String urlEndpoint;
	
	@Autowired
	private ObjectMapper mapper;
	
	private final String resource = "/clientes";
	
	@Autowired
	private RestTemplateBuilder builder;
	
	public Cliente inserir(Cliente novoCliente) {
		
		RestTemplate httpClient = builder.build();
		
		URI uri = httpClient.postForLocation(urlEndpoint + resource, novoCliente);
		
		Cliente clienteSalvo = httpClient.getForObject(urlEndpoint + resource + uri.getPath(), Cliente.class);
		
		return clienteSalvo;
	}
	
	public Cliente buscarPor(Integer codigo) {
		
		RestTemplate httpClient = builder.build();
		
		Cliente clienteEncontrado = 
				httpClient.getForObject(urlEndpoint + resource + "/codigo/" + codigo, Cliente.class);
		
		return clienteEncontrado;
	}
	
	public void alterar(Cliente clienteSalvo) {
		RestTemplate httpClient = builder.build();
		httpClient.put(urlEndpoint + resource, clienteSalvo);
	}
	
	public void excluir(Cliente clienteSalvo) {
		RestTemplate httpClient = builder.build();
		httpClient.delete(urlEndpoint + resource + "/codigo/" + clienteSalvo.getCodigo());
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> listarPor(String nomeCompleto){
		
		RestTemplate httpClient = builder.build();
		
		List<LinkedHashMap<String, Object>> response = httpClient.getForObject(
				urlEndpoint + resource + "/nome-completo/" + nomeCompleto, List.class);
		
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		for (LinkedHashMap<String, Object> c : response) {
			Cliente cliente = mapper.convertValue(c, Cliente.class);
			clientes.add(cliente);
		}
		
		return clientes;
	}
	
	public Cliente buscarPorUsuarioCom(Integer codigo) {
		
		RestTemplate httpClient = builder.build();
		
		Cliente clienteEncontrado = 
				httpClient.getForObject(urlEndpoint + resource + "/codigo-usuario/" + codigo, Cliente.class);
		
		return clienteEncontrado;
	}
	
	public List<Cliente> listarTodos(){
		
		RestTemplate httpClient = builder.build();
		
		@SuppressWarnings("unchecked")
		List<LinkedHashMap<String, Object>> response = httpClient.getForObject(
				urlEndpoint + resource, List.class);
		
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		for (LinkedHashMap<String, Object> c : response) {
			Cliente cliente = mapper.convertValue(c, Cliente.class);
			clientes.add(cliente);
		}
		
		return clientes;
	}
	

}
