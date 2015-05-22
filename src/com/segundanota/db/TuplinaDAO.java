package com.segundanota.db;

import java.util.LinkedList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.segundanota.model.Disciplina;
import com.segundanota.model.Escola;
import com.segundanota.model.Tuplina;
import com.segundanota.model.Turma;

public class TuplinaDAO {
	
	public static LinkedList<Tuplina> getTuplinas(Conexao con) {
		SQLiteDatabase db = con.getConexao();
		String sql = "SELECT td.idTuplina, d.idDisciplina, d.nome, d.cargahoraria, "  +
				   	 " t.idTurma, t.descricao, t.turno, t.ano, e.idEscola, e.nome" +
					 " FROM tuplina td,disciplina d,turma t ,escola e " +
				     " WHERE  d.idDisciplina = td.fk_idDisciplina " +    
				     " AND t.idTurma = td.fk_idTurma";
		Cursor c = db.rawQuery(sql, null);
		LinkedList <Tuplina> listaTuplinas = new LinkedList<Tuplina>();
		while(c.moveToNext()){
			Tuplina objTuplina = new Tuplina();
			//Preencho Tuplina
			objTuplina.setIdTuplina(c.getInt(0));
			//Preencho Disciplina
			Disciplina objDisciplina = objTuplina.getDisciplina();
			objDisciplina.setIdDisciplina(c.getInt(1));
			objDisciplina.setNome(c.getString(2));
			objDisciplina.setCargaHoraria(c.getInt(3));
			//Preencho turma
			Turma objTurma = objTuplina.getTurma();
			objTurma.setIdTurma(c.getInt(4));
			objTurma.setDescricao(c.getString(5));
			objTurma.setTurno(c.getString(6));
			objTurma.setAno(c.getInt(7));
			//Preencho Escola
			Escola objEscola = objTurma.getEscola();
			objEscola.setIdEscola(c.getInt(8));
			objEscola.setNome(c.getString(9));
			listaTuplinas.add(objTuplina);
		}
		return listaTuplinas;
		

	}
	
	public static Tuplina getTuplinaById(Conexao con,int id){
		SQLiteDatabase db = con.getConexao();
		String sql = "SELECT td.idTuplina, d.idDisciplina, d.nome, d.cargahoraria, "  +
				   	 " t.idTurma, t.descricao, t.turno, t.ano, e.idEscola, e.nome" +
					 " FROM tuplina td,disciplina d,turma t ,escola e " +
				     " WHERE td.idTuplina = ?" +
				     " AND d.idDisciplina = td.fk_idDisciplina " +    
				     " AND t.idTurma = td.fk_idTurma" +
				     " AND e.idEscola = t.fk_idEscola";
		
		String [] params = {Integer.toString(id)};
		
		Cursor c = db.rawQuery(sql, params);
		if(c.moveToNext()){
			Tuplina objTuplina = new Tuplina();
			//Preencho Tuplina
			objTuplina.setIdTuplina(c.getInt(0));
			//Preencho Disciplina
			Disciplina objDisciplina = objTuplina.getDisciplina();
			objDisciplina.setIdDisciplina(c.getInt(1));
			objDisciplina.setNome(c.getString(2));
			objDisciplina.setCargaHoraria(c.getInt(3));
			//Preencho turma
			Turma objTurma = objTuplina.getTurma();
			objTurma.setIdTurma(c.getInt(4));
			objTurma.setDescricao(c.getString(5));
			objTurma.setTurno(c.getString(6));
			objTurma.setAno(c.getInt(7));
			//Preencho Escola
			Escola objEscola = objTurma.getEscola();
			objEscola.setIdEscola(c.getInt(8));
			objEscola.setNome(c.getString(9));
			
			return objTuplina;
		}
		else{
			return null;
		}
		

		
	}


}
