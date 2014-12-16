package com.br.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.br.bd.DBHelper;
import com.br.model.Emprestimo;
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

	public List<Emprestimo_has_Livros> list(int prIdPessoa,int prEmprestimo) {
		PessoaDao daoPessoa = new PessoaDao(context);	
		LivroDao daoLivro = new LivroDao(context);
		EmprestimoDao daoEmprestimo = new EmprestimoDao(context);
		List<Emprestimo_has_Livros> lista = new ArrayList<Emprestimo_has_Livros>();
		try{//lendo redos da database
			this.dataBase = this.openHelper.getReadableDatabase();
			String sql ="SELECT * FROM Emprestimo e inner join "
			+table_name+" el on (e.idEmprestimo=el.idEmprestimo) where (e.idPessoa="+prIdPessoa +") and (el.status =1) and (el.idEmprestimo ="+prEmprestimo+")";			 
		    String[] selectionArgs = new String[] {"idPessoa"};  
		    Cursor cursor = this.dataBase.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
                do {
                    		Emprestimo_has_Livros modelo = new Emprestimo_has_Livros();		
                    		//Emprestimo entidade = new Emprestimo();
                            modelo.setEmprestimo(daoEmprestimo.getEntidade(cursor.getInt(cursor.getColumnIndex("idEmprestimo"))));
                            //setando o livro
                            modelo.setLivro(daoLivro.getEntidade(cursor.getInt(cursor.getColumnIndex("idLivro"))));
                            //pegando a data da devolucao
                            long data = cursor.getLong(cursor.getColumnIndex("dataDevolucao"));
                            Date d = new Date(data);
                            modelo.setDataDevolucao(d);
                            //pegando a data 
                            long data2 = cursor.getLong(cursor.getColumnIndex("dataDevolvido"));
                            Date d2 = new Date(data);
                            modelo.setDataDevolvido(d2);                            
                            modelo.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                            
                    lista.add(modelo);

                } while (cursor.moveToNext());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return lista;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}
}
