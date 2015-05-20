package com.segundanota.db;

import android.database.sqlite.*;
import android.content.Context;


public class Conexao {
	private String alias = "caderneta.db";
	private Context ctx;
	
	public Conexao(Context ctx){
		this.ctx = ctx;
	}
	
	public SQLiteDatabase getConexao(){
		return ctx.openOrCreateDatabase(this.alias, Context.MODE_PRIVATE, null);
	}

}
