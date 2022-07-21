package br.com.senai.sa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.senai.sa.entity.enums.Perfil;
import br.com.senai.sa.validation.AoAlterar;
import br.com.senai.sa.validation.AoInserir;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Usuario")
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "O código do usuario deve ser obrigatório", groups = AoAlterar.class)
	@Null(message = "O código do usuario deve ser nulo", groups = AoInserir.class)
	@EqualsAndHashCode.Include
	private Integer codigo;
	
	@Column(name = "login")
	@NotEmpty(message = "O login do cliente é obrigatório")
	@Size(min=2, max = 20, message = "O tamanho do login do usuário deve estar entre 2 e 20 caracteres")
	private String login;
	
	@Column(name = "senha")
	@NotEmpty(message = "A senha do cliente é obrigatória")
	@Size(min=2, max = 10, message = "A senha do usuário deve estar entre 2 e 10 caracteres")
	private String senha;
	
	@Column(name = "perfil")
	@NotNull(message = "O perfil do usuário não deve ser nulo")
	@Enumerated(EnumType.STRING)
	private Perfil perfil;

}
