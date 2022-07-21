package br.com.senai.sa.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.senai.sa.validation.AoAlterar;
import br.com.senai.sa.validation.AoInserir;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Cliente")
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "O código do cliente deve ser obrigatório", groups = AoAlterar.class)
	@Null(message = "O código do cliente deve ser nulo", groups = AoInserir.class)
	@EqualsAndHashCode.Include
	private Integer codigo;
	
	@Column(name = "nm_completo")
	@NotEmpty(message = "O nome completo do cliente é obrigatório")
	@Size(min=2, max = 50, message = "O tamanho do nome completo do cliente deve estar entre 2 e 50 caracteres")
	private String nomeCompleto;
	
	@Column(name = "cpf")
	@NotEmpty(message = "O cpf do cliente é obrigatório")
	@Size(min = 14, max = 14, message = "O tamanho do cpf do cliente deve ser 14 caracteres")
	@Pattern(regexp = "(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)", message = "O formato do CPF deve ser 'NNN.NNN.NNN-NN'")
	private String cpf;
	
	@Column(name = "dt_admissao")
	@NotNull(message = "A data de admissão do cliente deve ser obrigatória")
	private LocalDate dataDeAdmissao;
	
	@Column(name = "rg")
	@NotEmpty(message = "O rg do atleta é obrigatório")
	@Size(min = 10, max = 10, message = "O tamanho do rg do cliente deve possuir 10 caracteres")
	@Pattern(regexp = "(^\\d{2}\\x2E\\d{3}\\x2E\\d{3}$)", message = "O formato do RG deve ser 'NN.NNN.NNN'")
	private String rg;
	
	@Column(name = "endereco_completo")
	@NotEmpty(message = "O endereço completo do cliente é obrigatório")
	@Size(min=2, max = 500, message = "O tamanho do endereço completo do cliente deve estar entre 2 e 500 caracteres")
	private String enderecoCompleto;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "codigo_usuario")
	@NotNull(message = "O usuário do cliente deve ser obrigatório")
	private Usuario usuario;

}
