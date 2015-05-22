package com.segundanota.model;

import java.io.Serializable;

//Deixa a classe Serializavel para poder passá-la pelo Bundle
public class Aula implements Serializable {
	private int idAula;
	private String conteudo;
	private String data;
	private Tuplina tuplina;
	
	public Aula(){
		this.tuplina = new Tuplina();
	}
	
	public int getIdAula() {
		return idAula;
	}
	public void setIdAula(int idAula) {
		this.idAula = idAula;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Tuplina getTuplina() {
		return this.tuplina;
	}
	public void setTuplina(Tuplina Tuplina) {
		this.tuplina = Tuplina;
	}

	
	
	

}
