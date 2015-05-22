package com.segundanota.model;

import java.io.Serializable;

public class Escola implements Serializable{
	private int idEscola;
	private String nome;
	
	public int getIdEscola() {
		return idEscola;
	}
	public void setIdEscola(int idEscola) {
		this.idEscola = idEscola;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	

}
