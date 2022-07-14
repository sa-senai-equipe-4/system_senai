package br.com.senai.sa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.senai.sa.dto.enums.Quitado;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Promissoria {
	
	@EqualsAndHashCode.Include
	private Integer codigo;
	
	private BigDecimal valor;
	
	private LocalDate dataDeVencimento;
	
	private Quitado quitado;
	
	private String descricao;
	
	private Cliente cliente;
	
}
