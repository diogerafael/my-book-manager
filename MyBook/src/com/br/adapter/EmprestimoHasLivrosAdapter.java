package com.br.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.br.adapter.EmprestimoAdapter.ViewHolder;
import com.br.bybook.R;
import com.br.model.Emprestimo;
import com.br.model.Emprestimo_has_Livros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EmprestimoHasLivrosAdapter extends BaseAdapter{

	private List<Emprestimo_has_Livros> lista;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	
	
	
	static class ViewHolder {
        private TextView tvLivro;
        private TextView tvDataDevolucao;
    }

	public EmprestimoHasLivrosAdapter(Context context,List<Emprestimo_has_Livros> lista){
		mInflater = LayoutInflater.from(context);
		this.lista = lista;
	}
	


	@Override
	public int getCount() {
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
		//return lista.get(arg0).getEmprestimo().getIdEmprestimo();
		return 0;
	}



	@Override
	public View getView(int posicao, View convertView, ViewGroup arg2) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.tem_lis_emprestimo_lista, null);
			holder = new ViewHolder();			
			holder.tvLivro = (TextView) convertView.findViewById(R.id.item_emprestimo_livro);
			holder.tvDataDevolucao = (TextView)convertView.findViewById(R.id.item_emprestimo_data);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		com.br.model.Emprestimo_has_Livros e = this.lista.get(posicao);

		holder.tvLivro.setText(e.getLivro().getDescricao());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String data = format.format(e.getDataDevolucao());
		holder.tvDataDevolucao.setText(data);

		return convertView;

	}
}
