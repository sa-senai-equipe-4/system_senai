package br.com.senai.sa.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {
	
	@EqualsAndHashCode.Include
	private Integer codigo;
	
	private LocalDate dataDeAdmissao;
	
	private String rg;
	
	private String cpf;
	
	private String enderecoCompleto;
	
	private String nomeCompleto;
	
	private Usuario usuario;

	@Override
	public String toString() {
		return nomeCompleto;
	}
	
	

}
