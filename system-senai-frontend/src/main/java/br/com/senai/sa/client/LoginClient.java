package br.com.senai.sa.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.senai.sa.dto.Usuario;

@Component
public class LoginClient {
	
	@Value("${endpoint}")
	private String urlEndpoint;
	
	private final String resource = "/usuarios/login";
	
	@Autowired
	private RestTemplateBuilder builder;
	
	public Usuario loginPor(String login, String senha) {
		
		RestTemplate httpClient = builder.build();
		
		Usuario usuarioLogado = 
				httpClient.getForObject(
						urlEndpoint + resource + "?login=" + login + "&senha=" + senha, Usuario.class);
		
		return usuarioLogado;
	}
	
}
