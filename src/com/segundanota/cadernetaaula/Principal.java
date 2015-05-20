package com.segundanota.cadernetaaula;

import java.util.LinkedList;

import com.segundanota.adapter.AulaAdapter;
import com.segundanota.db.AulaDAO;
import com.segundanota.db.Conexao;
import com.segundanota.model.Aula;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Principal extends Activity implements OnItemClickListener {
	private ListView listAulas;
	private LinkedList<Aula> aulas;
	private Conexao conexao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		listAulas = (ListView) findViewById(R.id.listAulas);
		listAulas.setOnItemClickListener(this);
		this.conexao = new Conexao(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		carregarLista();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}else if (id == R.id.action_cadastrarAula){
			Intent it = new Intent(this, AulaActivity.class);
			Bundle parametros = new Bundle();
			parametros.putSerializable("aula", null); //Passo como null pois não é uma inserção
			it.putExtras(parametros);
			startActivity(it);
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void carregarLista(){
		this.aulas = AulaDAO.exibeValores(this.conexao);
		AulaAdapter meuAdapter = new AulaAdapter(this, aulas);
		listAulas.setAdapter(meuAdapter);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Bundle parametros = new Bundle();
		Intent it = new Intent(this,AulaActivity.class);
		Aula aula =  (Aula) listAulas.getAdapter().getItem(position); //Pego o objeto Aula selecionado
		//Irei passar o id da aula selecionada como parâmetro para a outra activity
		parametros.putSerializable("aula", aula);
		it.putExtras(parametros);
		startActivity(it);
	}
}
