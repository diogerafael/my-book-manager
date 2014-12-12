package com.br.model;

import java.util.List;

import com.br.dao.AbstractDao;
import com.br.dao.InterfaceDao;

public class Livro  {
	private Integer idLivro;
	private String descricao;
	private String foto;
	private String serie;
	private Integer quantidade;
	
	

	
	public Livro() {
		super();
	}


	public Livro(Integer idLivro, String descricao, String foto, String serie,
			Integer quantidade) {
		super();
		this.idLivro = idLivro;
		this.descricao = descricao;
		this.foto = foto;
		this.serie = serie;
		this.quantidade = quantidade;
	}
	
	
	public Integer getIdLivro() {
		return idLivro;
	}
	public void setIdLivro(Integer idLivro) {
		this.idLivro = idLivro;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	
	
	
	
}
