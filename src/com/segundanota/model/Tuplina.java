package com.segundanota.model;

import java.io.Serializable;

public class Tuplina implements Serializable{
	private int idTuplina;
	private Disciplina disciplina;
	private Turma turma;
	
	
	public Tuplina() {
		// TODO Auto-generated constructor stub
		this.disciplina = new Disciplina();
		this.turma = new Turma();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.turma.getDescricao() + " - " + this.disciplina.getNome();
	}
	
	public int getIdTuplina() {
		return idTuplina;
	}
	public void setIdTuplina(int idTuplina) {
		this.idTuplina = idTuplina;
	}
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	public Turma getTurma() {
		return turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}

}
