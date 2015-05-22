package com.segundanota.cadernetaaula;

import java.util.LinkedList;

import com.segundanota.db.AulaDAO;
import com.segundanota.db.Conexao;
import com.segundanota.db.TuplinaDAO;
import com.segundanota.model.Aula;
import com.segundanota.model.Tuplina;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.MediaDescriptionCompatApi21.Builder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AulaActivity extends Activity implements OnItemSelectedListener {

	private TextView txtNovaData;
	private TextView txtNovoConteudo;
	private Spinner cbNovoTuplina;
	private Conexao conexao;
	private Tuplina objTuplina; // Refer�ncia do objeto que est� selecionado no
								// Spinner
	protected Aula objAula; // Esse objeto ir� ser preenchido caso seja uma
							// edi��o ou exclus�o

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aula);
		this.conexao = new Conexao(this);
		this.txtNovaData = (TextView) findViewById(R.id.txtNovaData);
		this.txtNovoConteudo = (TextView) findViewById(R.id.txtNovoConteudo);
		this.cbNovoTuplina = (Spinner) findViewById(R.id.cbTuplinas);
		// Preencherei o spinner com as Tuplinas cadastradas
		LinkedList<Tuplina> listaTuplinas = TuplinaDAO
				.getTuplinas(this.conexao);
		ArrayAdapter<Tuplina> tuplinasAdapter = new ArrayAdapter<Tuplina>(this,
				android.R.layout.simple_spinner_item, listaTuplinas);
		cbNovoTuplina.setAdapter(tuplinasAdapter);
		cbNovoTuplina.setOnItemSelectedListener(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.objAula = null; // Sempre que exibe a tela inicializo com null
		// Irei buscar se existe uma aula no bundle
		Intent it = getIntent();
		Bundle parametros = it.getExtras();
		if (parametros.getSerializable("aula") != null) {
			this.objAula = (Aula) parametros.getSerializable("aula");
			this.atualizaView();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aula, menu);
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
		} else if (id == R.id.action_remover) {
			// S� exclui se est� em uma edi��o
			// Se objAula n�o � nulo, significa que pegamos uma aula pelo bundle
			if (this.objAula != null) {
				this.removerAula();
			} else {
				Toast.makeText(this, "N�o � uma edi��o", Toast.LENGTH_SHORT)
						.show();
			}

		}
		return super.onOptionsItemSelected(item);
	}

	public void onButtonClick(View v) {
		// Primeiro pego os valores
		Aula aula = new Aula();
		aula.setData(txtNovaData.getText().toString());
		aula.setConteudo(txtNovoConteudo.getText().toString());
		aula.setTuplina(this.objTuplina); // Pego a Tuplina selecionada
		if (this.objAula != null) { // Se n�o for nulo significa que � uma
									// altera��o de uma aula j� existente
			this.alterarAula(aula);
		} else {
			this.cadastrarAula(aula);
		}
		this.finish();

	}

	// Coloca na View os valores armazenados no objAula
	public void atualizaView() {
		this.txtNovaData.setText(this.objAula.getData());
		this.txtNovoConteudo.setText(this.objAula.getConteudo());
		// Como tenho um adapter de Objetos, vou ter que percorrer para
		// encontrar o objeto que estou procurando e selecion�-lo no spinner.
		// Farei a compara��o por id
		ArrayAdapter<Tuplina> adapterTuplina = (ArrayAdapter<Tuplina>) this.cbNovoTuplina
				.getAdapter();
		for (int i = 0; i < this.cbNovoTuplina.getCount(); i++) {
			if (adapterTuplina.getItem(i).getIdTuplina() == this.objAula
					.getTuplina().getIdTuplina()) {
				this.cbNovoTuplina.setSelection(i);
				break;
			}
		}

	}

	private void removerAula() {
		// Utilizaremos um alertDialog para confirmar a exclus�o
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("CONFIRMAR");
		alertBuilder.setMessage("Deseja excluir essa aula?");
		alertBuilder.setPositiveButton("Sim", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (AulaDAO.removeValores(objAula, conexao)) {
					Toast.makeText(AulaActivity.this, "Aula exclu�da!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AulaActivity.this,
							"N�o foi poss�vel excluir  aula!",
							Toast.LENGTH_SHORT).show();
				}
				AulaActivity.this.finish();
			}
		});
		alertBuilder.setNegativeButton("N�o", null);
		alertBuilder.create().show();

	}

	private void cadastrarAula(Aula newAula) {
		if (AulaDAO.insereValores(newAula, this.conexao)) {
			Toast.makeText(this, "Aula cadastrada!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "N�o foi poss�vel inserir aula!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void alterarAula(Aula editAula) {
		// Seto o id da aula que vai ser alterada com o id que peguei do bundle
		editAula.setIdAula(this.objAula.getIdAula());
		if (AulaDAO.atualizaValores(editAula, conexao)) {
			Toast.makeText(this, "Aula alterada!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "N�o foi poss�vel alterar aula!",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		this.objTuplina = (Tuplina) parent.getSelectedItem();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
