package com.br.model;

public class Pessoa {
	private Integer idPessoa;
	private String nome;
	private String foto;
	private String telefone;
	private String email;
	
	public Pessoa(Integer idPessoa, String nome,String foto, String telefone,String email) {
		super();
		this.idPessoa = idPessoa;
		this.nome = nome;
		this.foto = foto;
		this.telefone = telefone;
		this.email = email;
	}
	public Pessoa() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
