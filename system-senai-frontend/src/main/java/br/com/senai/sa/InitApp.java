package br.com.senai.sa;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.HttpClientErrorException;

import br.com.senai.sa.view.TelaLogin;

@SpringBootApplication
public class InitApp {

	public static void main(String[] args) {
		SpringApplicationBuilder builder = 
				new SpringApplicationBuilder(InitApp.class);
		builder.headless(false);
		builder.run(args);
	}
	
	@Autowired
	private TelaLogin login;
	
	@Bean	
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			try {				
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {	
							login.setLocationRelativeTo(null);
							login.setVisible(true);
						} catch (Exception e) {							
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
					}
				});
				
				
//				Usuario usuarioLogado = loginClient.loginPor("laudelino.neto", "123456");
//				System.out.println(usuarioLogado);
				
				
				
//				Promissoria promissoria = new Promissoria();
//				promissoria.setDataDeVencimento(LocalDate.of(2022, 12, 25));
//				promissoria.setDescricao("pague esta divida");
//				promissoria.setQuitado(Quitado.NAO);
//				promissoria.setValor(new BigDecimal(250));
//				
//				Cliente cliente = clienteClient.buscarPor(6);
//				promissoria.setCliente(cliente);
//				
//				Promissoria promissoriaSalvo = promissoriaClient.inserir(promissoria);
//				System.out.println(promissoriaSalvo);
						
						
						
				
//				Usuario usuario = new Usuario();
//				usuario.setLogin("kevin.demetro");
//				usuario.setPerfil(Perfil.CLIENTE);
//				usuario.setSenha("123456");
//				
//				Cliente cliente = new Cliente();
//				cliente.setCpf("000.000.000-00");
//				cliente.setDataDeAdmissao(LocalDate.of(2022, 7, 14));
//				cliente.setEnderecoCompleto("Rau qualeuer");
//				cliente.setNomeCompleto("Kevin Demetro");
//				cliente.setRg("00.000.000");
//				cliente.setUsuario(usuario);
//				
//				Cliente clienteSalvo = clienteClient.inserir(cliente);
//				System.out.println(clienteSalvo);

						
						
//				List<Cliente> clientes = clienteClient.listarPor("a");
//				System.out.println(clientes);
//				
//				List<Promissoria> promissorias = promissoriaClient.listarPor("a");
//				System.out.println(promissorias);
				
			}catch (HttpClientErrorException e) {
				JSONObject erro = new JSONObject(e.getResponseBodyAsString());
				JSONObject ex = new JSONObject(erro.getJSONArray("erros").getJSONObject(0).toString());
				String msg = ex.get("mensagem").toString();
				System.out.println(msg);
			}
			
		};
	}

}
