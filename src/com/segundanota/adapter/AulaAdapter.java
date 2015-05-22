package com.segundanota.adapter;

import java.util.LinkedList;

import com.segundanota.model.Aula;
import com.segundanota.cadernetaaula.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AulaAdapter extends BaseAdapter {
	private Context ctx;
	private LinkedList<Aula> listaAulas;
	
	public AulaAdapter(Context ctx, LinkedList<Aula> aulas){
		this.listaAulas = aulas;
		this.ctx = ctx;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listaAulas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.listaAulas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Aula aula = listaAulas.get(position);
		//Transformo o xml que contem a estrutura da lista em um objeto do tipo View
		View itemLista = LayoutInflater.from(ctx).inflate(R.layout.item_lista, null);
		
		//Agora jogo os atributos nos componentes visuais da tela
		TextView txtIdAula = (TextView) itemLista.findViewById(R.id.txtIdAula);
		txtIdAula.setText(txtIdAula.getText() + Integer.toString(aula.getIdAula()));
		TextView txtConteudo = (TextView) itemLista.findViewById(R.id.txtConteudo);
		txtConteudo.setText(txtConteudo.getText() + aula.getConteudo());
		TextView txtData = (TextView) itemLista.findViewById(R.id.txtData);
		txtData.setText(txtData.getText() + aula.getData());
		TextView txtTuplina = (TextView) itemLista.findViewById(R.id.txtTuplina);
		//Pego a tuplina
    	txtTuplina.setText(txtTuplina.getText() + aula.getTuplina().toString());

		
		
		return itemLista;
	}

}
