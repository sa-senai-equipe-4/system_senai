package br.com.senai.sa.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Cliente {
	
	@EqualsAndHashCode.Include
	private Integer codigo;
	
	private LocalDate dataDeAdmissao;
	
	private String rg;
	
	private String cpf;
	
	private String enderecoCompleto;
	
	private String nomeCompleto;
	
	private Usuario usuario;

}
