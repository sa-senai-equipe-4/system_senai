package br.com.senai.sa.exception;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class ErroFormatter {
	
	public String formatar (HttpClientErrorException ex) {
		JSONObject erro = new JSONObject(ex.getResponseBodyAsString());
		JSONObject error = new JSONObject(erro.getJSONArray("erros").getJSONObject(0).toString());
		return error.get("mensagem").toString();
	}

}
