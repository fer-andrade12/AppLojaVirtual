package com.App.LojaVirtual.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name="id")
public class PessoaJuridica extends Pessoa{
	
	@Column(nullable=false)
	private String cnpj;
	
	private String inscEstatudal;
	
	private String inscMunicipal;
	
	@Column(nullable=false)
	private String razaoSocial;
	
	@Column(nullable=false)
	private String nomeFantasia;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscEstatudal() {
		return inscEstatudal;
	}

	public void setInscEstatudal(String inscEstatudal) {
		this.inscEstatudal = inscEstatudal;
	}

	public String getInscMunicipal() {
		return inscMunicipal;
	}

	public void setInscMunicipal(String inscMunicipal) {
		this.inscMunicipal = inscMunicipal;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

}
