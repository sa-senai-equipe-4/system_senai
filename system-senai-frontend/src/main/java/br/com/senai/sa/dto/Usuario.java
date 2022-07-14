package br.com.senai.sa.dto;

import br.com.senai.sa.dto.enums.Perfil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Usuario {
	
	@EqualsAndHashCode.Include
	private Integer codigo;
	
	private String login;
	
	private String senha;
	
	private Perfil perfil;

}
