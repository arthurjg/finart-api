package br.com.artsoft.finart.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.NaturalId;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class Usuario implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	public Usuario() {
		permissoes = new ArrayList<>();
	}
	
	public Usuario(Integer codigo, String nome, String login, String email, String senha, LocalDate nascimento,
			String celular, String idioma, boolean ativo, List<Permissao> permissoes) {		
		this();
		this.codigo = codigo;
		this.nome = nome;
		this.login = login;
		this.email = email;
		this.senha = senha;
		this.nascimento = nascimento;
		this.celular = celular;
		this.idioma = idioma;
		this.ativo = ativo;
		if(!Objects.isNull(permissoes) && !permissoes.isEmpty()) {
			this.permissoes.addAll(permissoes);
		}		
	}		
	
	@Id
	@GeneratedValue
	private Integer codigo;
	
	private String nome;
	
	@NaturalId
	private String login;
	private String email;	
	private String senha;
	private LocalDate nascimento;
	private String celular;
	private String idioma;	
	private boolean ativo;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Permissao> permissoes;		
	
	public boolean isAtivo() {
		return this.ativo;
	}		
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((celular == null) ? 0 : celular.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((idioma == null) ? 0 : idioma.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result
				+ ((nascimento == null) ? 0 : nascimento.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((permissoes == null) ? 0 : permissoes.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (ativo != other.ativo)
			return false;
		if (celular == null) {
			if (other.celular != null)
				return false;
		} else if (!celular.equals(other.celular))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (idioma == null) {
			if (other.idioma != null)
				return false;
		} else if (!idioma.equals(other.idioma))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nascimento == null) {
			if (other.nascimento != null)
				return false;
		} else if (!nascimento.equals(other.nascimento))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (permissoes == null) {
			if (other.permissoes != null)
				return false;
		} else if (!permissoes.equals(other.permissoes))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}	

}
