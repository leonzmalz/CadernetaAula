package com.segundanota.cadernetaaula;

import com.segundanota.db.AulaDAO;
import com.segundanota.db.Conexao;
import com.segundanota.model.Aula;

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
import android.widget.TextView;
import android.widget.Toast;

public class AulaActivity extends Activity {

	private TextView txtNovaData;
	private TextView txtNovoConteudo;
	private TextView txtNovoIdTuplina;
	private Conexao conexao;
	boolean resposta = false; // Flag de controle do alert
	protected Aula objAula; // Esse objeto irá ser preenchido caso seja uma
							// edição ou exclusão

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aula);
		this.txtNovaData = (TextView) findViewById(R.id.txtNovaData);
		this.txtNovoConteudo = (TextView) findViewById(R.id.txtNovoConteudo);
		this.txtNovoIdTuplina = (TextView) findViewById(R.id.txtNovoIdTuplina);
		this.conexao = new Conexao(this);

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
			// Só exclui se está em uma edição
			// Se objAula não é nulo, significa que pegamos uma aula pelo bundle
			if (this.objAula != null) {
				this.removerAula();
			} else {
				Toast.makeText(this, "Não é uma edição", Toast.LENGTH_SHORT)
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
		aula.setIdTuplina(Integer.parseInt(txtNovoIdTuplina.getText()
				.toString()));
		if (this.objAula != null) { // Se não for nulo significa que é uma
									// alteração de uma aula já existente
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
		this.txtNovoIdTuplina.setText(Integer.toString(this.objAula
				.getIdTuplina()));
	}

	private void removerAula() {
		// Utilizaremos um alertDialog para confirmar a exclusão
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("CONFIRMAR");
		alertBuilder.setMessage("Deseja excluir essa aula?");
		alertBuilder.setPositiveButton("Sim", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (AulaDAO.removeValores(objAula, conexao)) {
					Toast.makeText(AulaActivity.this, "Aula excluída!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AulaActivity.this,
							"Não foi possível excluir  aula!",
							Toast.LENGTH_SHORT).show();
				}
				AulaActivity.this.finish();
			}
		});
		alertBuilder.setNegativeButton("Não", null);
		alertBuilder.create().show();

	}

	private void cadastrarAula(Aula newAula) {
		if (AulaDAO.insereValores(newAula, this.conexao)) {
			Toast.makeText(this, "Aula cadastrada!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Não foi possível inserir aula!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void alterarAula(Aula editAula) {
		// Seto o id da aula que vai ser alterada com o id que peguei do bundle
		editAula.setIdAula(this.objAula.getIdAula());
		if (AulaDAO.atualizaValores(editAula, conexao)) {
			Toast.makeText(this, "Aula alterada!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Não foi possível alterar aula!",
					Toast.LENGTH_SHORT).show();
		}
	}


}
