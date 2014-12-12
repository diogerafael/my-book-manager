package com.br.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.br.bd.DBHelper;
import com.br.model.Emprestimo_has_Livros;

public class Emprestimo_has_LivrosDao extends AbstractDao implements InterfaceDao<Emprestimo_has_Livros>{

	private Context context;
	private String table_name = "Emprestimo_has_Livro";
	String []colunas = new String[] {"idLivro","idEmprestimo","dataDevolucao","status","dataDevolvido"};
	private EmprestimoDao daoEmprestimo;
	
	
	//_____________________constructor_______________
    public Emprestimo_has_LivrosDao(Context context){
        openHelper   =  new DBHelper(context);
        this.context = context;
    }
    
    
	
	
	@Override
	public long save(Emprestimo_has_Livros entidade) {
		open();		
		ContentValues values = new ContentValues();
		values.put(colunas[1], entidade.getEmprestimo().getIdEmprestimo());
		values.put(colunas[2], entidade.getDataDevolucao().getTime());
		values.put(colunas[3], entidade.getStatus());
		values.put(colunas[0], entidade.getLivro().getIdLivro());
		//values.put(colunas[4], entidade.getDataDevolvido().getTime());		
		return this.dataBase.insert(table_name, null, values);
	}

	@Override
	public long update(Emprestimo_has_Livros entidade) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public long delete(Emprestimo_has_Livros entidade) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public List<Emprestimo_has_Livros> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Emprestimo_has_Livros getEntidade(int prID) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
