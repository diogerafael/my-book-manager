package com.br.adapter;

import java.util.List;

import com.br.adapter.PessoaAdapter.ViewHolder;
import com.br.bybook.R;
import com.br.model.Livro;
import com.br.model.Pessoa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LivroAdapter extends BaseAdapter{

	private List<Livro> lista;
	private LayoutInflater mInflater;
	private ViewHolder holder; 
	
	
	static class ViewHolder {
        private TextView tvTitulo;
        private TextView tvFoto;
    }
	
	public LivroAdapter(Context context,List<Livro> lista){
		mInflater = LayoutInflater.from(context);
		this.lista = lista;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lista.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lista.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return lista.get(arg0).getIdLivro();
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup arg2) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_lis_livro, null);
			holder = new ViewHolder();
			
			holder.tvTitulo = (TextView) convertView.findViewById(R.id.item_nome);
			//holder.tvTelefone  = (TextView)	convertView.findViewById(R.id.item_telefone);	
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Livro p = this.lista.get(posicao);

		holder.tvTitulo.setText(p.getDescricao());
		//holder.tvTelefone.setText(p.getTelefone());

		return convertView;
	}
	
	
	

}
