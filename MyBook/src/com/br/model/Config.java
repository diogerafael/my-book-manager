package com.br.model;

public class Config {

	private Integer idConfig;
	private Integer enviarEmail;
	private Integer enviarSms;
	private String  conteudoEmail;
	private String  conteudoSms;
	private Integer diasEmprestimo;
	

	public Config() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Config(Integer idConfig, Integer enviarEmail, Integer enviarSms,
			String conteudoEmail, String conteudoSms,Integer prDiasEmprestimo) {
		super();
		this.idConfig = idConfig;
		this.enviarEmail = enviarEmail;
		this.enviarSms = enviarSms;
		this.conteudoEmail = conteudoEmail;
		this.conteudoSms = conteudoSms;
		this.diasEmprestimo = prDiasEmprestimo;
	}
	
	public Integer getEnviarSms() {
		return enviarSms;
	}
	public void setEnviarSms(Integer enviarSms) {
		this.enviarSms = enviarSms;
	}
	public Integer getIdConfig() {
		return idConfig;
	}
	public void setIdConfig(Integer idConfig) {
		this.idConfig = idConfig;
	}
	public Integer getEnviarEmail() {
		return enviarEmail;
	}
	public void setEnviarEmail(Integer enviarEmail) {
		this.enviarEmail = enviarEmail;
	}
	public String getConteudoEmail() {
		return conteudoEmail;
	}
	public void setConteudoEmail(String conteudoEmail) {
		this.conteudoEmail = conteudoEmail;
	}
	public String getConteudoSms() {
		return conteudoSms;
	}
	public void setConteudoSms(String conteudoSms) {
		this.conteudoSms = conteudoSms;
	}
	public Integer getDiasEmprestimo() {
		return diasEmprestimo;
	}
	public void setDiasEmprestimo(Integer diasEmprestimo) {
		this.diasEmprestimo = diasEmprestimo;
	}
			
}
