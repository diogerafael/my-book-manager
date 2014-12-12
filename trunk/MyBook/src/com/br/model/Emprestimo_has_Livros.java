package com.br.model;

import java.util.Date;
import java.util.List;

public class Emprestimo_has_Livros {
	private Livro livro;
	private Emprestimo emprestimo;
	private Date dataDevolucao;
	private Date dataDevolvido;
	private int status;
	
	

	
	public Emprestimo_has_Livros() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Emprestimo_has_Livros(Livro livro, Emprestimo emprestimo,
			Date dataDevolucao, Date dataDevolvido, int status) {
		super();
		this.livro = livro;
		this.emprestimo = emprestimo;
		this.dataDevolucao = dataDevolucao;
		this.dataDevolvido = dataDevolvido;
		this.status = status;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public Emprestimo getEmprestimo() {
		return emprestimo;
	}
	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}
	public Date getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	public Date getDataDevolvido() {
		return dataDevolvido;
	}
	public void setDataDevolvido(Date dataDevolvido) {
		this.dataDevolvido = dataDevolvido;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
		
}