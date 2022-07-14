package br.com.senai.sa.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.senai.sa.entity.enums.Quitado;
import br.com.senai.sa.validation.AoAlterar;
import br.com.senai.sa.validation.AoInserir;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Promissoria")
@Table(name = "promissorias")
public class Promissoria {
	
	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "O código da promissória deve ser obrigatório", groups = AoAlterar.class)
	@Null(message = "O código da promissória deve ser nulo", groups = AoInserir.class)
	@EqualsAndHashCode.Include
	private Integer codigo;
	
	@Column(name = "valor")
	@NotNull(message = "O valor da promissória não deve ser nulo ")
	@Positive(message = "O valor da promissória deve ser positivo")
	private BigDecimal valor;
	
	@Column(name = "dt_vencimento")
	@NotNull(message = "A data de vencimento da promissória deve ser obrigatório")
	private LocalDate dataDeVencimento;
	
	@Column(name = "quitado")
	@NotNull(message = "A quitação ou não da promissória deve ser informada")
	@Enumerated(EnumType.STRING)
	private Quitado quitado;
	
	@Column(name = "descricao")
	@NotEmpty(message = "A descricao da promissória não deve ser vazia")
	@Size(min=10, max = 1500, message = "O tamanho do da descrição da promissória deve estar entre 10 e 1500 caracteres")
	private String descricao;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "codigo_cliente")
	@NotNull(message = "O cliente da promissória é obrigatório")
	private Cliente cliente;

}
