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

import br.com.senai.sa.dto.Promissoria;

@Component
public class PromissoriaClient {
	
	@Value("${endpoint}")
	private String urlEndpoint;
	
	@Autowired
	private ObjectMapper mapper;
	
	private final String resource = "/promissorias";
	
	@Autowired
	private RestTemplateBuilder builder;
	
	public Promissoria inserir(Promissoria novaPromissoria) {
		
		RestTemplate httpClient = builder.build();
		
		URI uri = httpClient.postForLocation(urlEndpoint + resource, novaPromissoria);
		
		Promissoria promissoriaSalva = httpClient.getForObject(urlEndpoint + resource + uri.getPath(), Promissoria.class);
		
		return promissoriaSalva;
	}
	
	public Promissoria buscarPor(Integer codigo) {
		
		RestTemplate httpClient = builder.build();
		
		Promissoria promissoriaEncontrada = 
				httpClient.getForObject(urlEndpoint + resource + "/codigo/" + codigo, Promissoria.class);
		
		return promissoriaEncontrada;
	}
	
	public void alterar(Promissoria promissoriaSalva) {
		RestTemplate httpClient = builder.build();
		httpClient.put(urlEndpoint + resource, promissoriaSalva);
	}
	
	public void excluir(Promissoria promissoriaSalva) {
		RestTemplate httpClient = builder.build();
		httpClient.delete(urlEndpoint + resource + "/codigo/" + promissoriaSalva.getCodigo());
	}
	
	@SuppressWarnings("unchecked")
	public List<Promissoria> listarPor(String nomeCompleto){
		
		RestTemplate httpClient = builder.build();
		
		List<LinkedHashMap<String, Object>> response = httpClient.getForObject(
				urlEndpoint + resource + "?nome-completo=" + nomeCompleto, List.class);
		
		List<Promissoria> promissorias = new ArrayList<Promissoria>();
		
		for (LinkedHashMap<String, Object> p : response) {
			Promissoria promissoria = mapper.convertValue(p, Promissoria.class);
			promissorias.add(promissoria);
		}
		
		return promissorias;
	}

}
