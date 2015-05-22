package com.segundanota.db;

import java.util.LinkedList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.segundanota.model.Aula;

public abstract class AulaDAO {

	public static boolean insereValores(Aula aula, Conexao con) {
		SQLiteDatabase db = con.getConexao();
		String sql = "INSERT INTO aula (conteudo,data,fk_idTuplina) VALUES (?,?,?)";
		// Pego os parametros
		String[] params = { aula.getConteudo(), aula.getData(),
				Integer.toString(aula.getTuplina().getIdTuplina()) };
		try {
			db.execSQL(sql, params);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	public static boolean removeValores(Aula aula, Conexao con) {
		SQLiteDatabase db = con.getConexao();
		String sql = "DELETE FROM aula WHERE idAula = ?";
		// Pego os parametros
		Object[] params = { aula.getIdAula() };
		try {
			db.execSQL(sql, params);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static boolean atualizaValores(Aula aula, Conexao con) {
		SQLiteDatabase db = con.getConexao();
		String sql = "UPDATE aula SET conteudo = ?, data = ?, fk_idTuplina = ? WHERE idAula = ?";
		// Pego os parametros
		String[] params = { aula.getConteudo(), aula.getData(),
				Integer.toString(aula.getTuplina().getIdTuplina()),
				Integer.toString(aula.getIdAula()) };
		try {
			db.execSQL(sql, params);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static LinkedList<Aula> exibeValores(Conexao con) {
		SQLiteDatabase db = con.getConexao();
		String sql = "SELECT idAula,conteudo,data,fk_idTuplina FROM aula";
		Cursor c = db.rawQuery(sql, null);
		
		LinkedList <Aula> listaAulas = new LinkedList<Aula>();
		while(c.moveToNext()){
			Aula objAula = new Aula();
			objAula.setIdAula(c.getInt(0));
			objAula.setConteudo(c.getString(1));
			objAula.setData(c.getString(2));
			//Seto a tuplina já pegando todos os valores referentes a turma, disciplina e escola
			objAula.setTuplina(TuplinaDAO.getTuplinaById(con, c.getInt(3)));
			listaAulas.add(objAula);
		}
		return listaAulas;
		

	}


}
