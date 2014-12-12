package com.br.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.SimpleFormatter;

import com.br.adapter.PessoaAdapter.ViewHolder;
import com.br.bybook.R;
import com.br.model.Emprestimo;
import com.br.model.Pessoa;




import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EmprestimoAdapter extends BaseAdapter{

	private List<Emprestimo> lista;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	
	
	
	static class ViewHolder {
        private TextView tvDescricao;
        private TextView tvData;
    }
	
	public EmprestimoAdapter(Context context,List<Emprestimo> lista){
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
		return lista.get(arg0).getIdEmprestimo();
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_lis_emprestimo, null);
			holder = new ViewHolder();			
			holder.tvDescricao = (TextView) convertView.findViewById(R.id.item_emprestimo_livro);
			holder.tvData = (TextView)convertView.findViewById(R.id.item_emprestimo_data);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		com.br.model.Emprestimo e = this.lista.get(posicao);

		holder.tvDescricao.setText(String.valueOf(e.getIdEmprestimo()));
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String data = format.format(e.getData());
		holder.tvData.setText(data);

		return convertView;

	}

}
