package com.br.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Emprestimo {
	private Integer idEmprestimo;
	private Date data;
	private Pessoa pessoa;
	private int status;
	private List<Livro> listaLivro;
	
	public Emprestimo(Integer idEmprestimo, Date data, Pessoa pessoa,int status) {
		super();
		this.idEmprestimo = idEmprestimo;
		this.data = data;
		this.pessoa = pessoa;
		this.status = status;
		this.listaLivro = new ArrayList<Livro>();
	}
	
	public Emprestimo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getIdEmprestimo() {
		return idEmprestimo;
	}
	public void setIdEmprestimo(Integer idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Livro> getListaLivro() {
		if(this.listaLivro == null){
			this.listaLivro = new ArrayList<Livro>();
		}
		return listaLivro;
	}
	public void setListaLivro(List<Livro> listaLivro) {
		this.listaLivro = listaLivro;
	}

	
}
