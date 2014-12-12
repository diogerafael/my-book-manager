package com.br.adapter;

import java.util.List;



import com.br.bybook.R;
import com.br.model.Pessoa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PessoaAdapter extends BaseAdapter {

	private List<Pessoa> lista;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	
	
	
	static class ViewHolder {
        private TextView tvDescricao;
        private TextView tvTelefone;
    }
	
	
	public PessoaAdapter(Context context,List<Pessoa> lista){
		mInflater = LayoutInflater.from(context);
		this.lista = lista;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.lista.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.lista.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup arg2) {
		
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_lis_pessoa, null);
			holder = new ViewHolder();
			
			holder.tvDescricao = (TextView) convertView.findViewById(R.id.item_nome);
			holder.tvTelefone  = (TextView)	convertView.findViewById(R.id.item_telefone);	
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Pessoa p = this.lista.get(posicao);

		holder.tvDescricao.setText(p.getNome());
		holder.tvTelefone.setText(p.getTelefone());

		return convertView;
		
	}

}