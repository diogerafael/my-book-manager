package com.br.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.br.bd.DBHelper;
import com.br.model.Livro;
import com.br.model.Pessoa;

public class PessoaDao extends AbstractDao implements InterfaceDao<Pessoa>{
	private Context context;
	private String table_name = "Pessoa";
    private String[] colunas = new String[] {"idPessoa", "nome","foto","telefone","email"};
	
     public PessoaDao(Context ctx){
    	openHelper = new DBHelper(ctx);
    	this.context = ctx;
    }
    
	@Override
	public long save(Pessoa entidade) {
		//
		open();//abrindo conexao com banco
		ContentValues values = new ContentValues();
		try{
		values.put("nome", entidade.getNome());
		values.put("foto", entidade.getFoto());
		values.put("telefone", entidade.getTelefone());
		values.put("email", entidade.getEmail());
		return this.dataBase.insert(table_name, null, values);
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public long update(Pessoa entidade) {
		// TODO Auto-generated method stub
		open();
		String filter = "idPessoa="+entidade.getIdPessoa();
		ContentValues values = new ContentValues();
		values.put(colunas[1], entidade.getNome());
		values.put(colunas[2], entidade.getFoto());
		values.put(colunas[3], entidade.getTelefone());
		values.put(colunas[4], entidade.getEmail());
		return this.dataBase.update(table_name,values,filter,null);
	}

	@Override
	public long delete(Pessoa entidade) {
		// TODO Auto-generated method stub
		open();
        String filter = "idPessoa="+entidade.getIdPessoa();
        return this.dataBase.delete(table_name,filter,null);
	}

	@Override
	public List<Pessoa> list() {
		// TODO Auto-generated method stub
		List<Pessoa> lista = new ArrayList<Pessoa>();
		try{//lendo redos da database
			this.dataBase = this.openHelper.getReadableDatabase();
			Cursor cursor = this.dataBase.query(table_name,colunas,null, null, null, null, "nome desc");
			if (cursor.moveToFirst()) {
                do {
                    Pessoa entidade = new Pessoa();
                            entidade.setIdPessoa(cursor.getInt(cursor.getColumnIndex(colunas[0])));
                    		entidade.setNome(cursor.getString(cursor.getColumnIndex(colunas[1])));
                    		entidade.setFoto(cursor.getString(cursor.getColumnIndex(colunas[2])));
                    		entidade.setTelefone(cursor.getString(cursor.getColumnIndex(colunas[3])));
                    		entidade.setEmail(cursor.getString(cursor.getColumnIndex(colunas[4])));
                    lista.add(entidade);

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

	@Override
	public Pessoa getEntidade(int prID) {
		// TODO Auto-generated method stub
		Pessoa entidade =null;
		this.dataBase = this.openHelper.getReadableDatabase();		
		String sql = "SELECT * FROM " + table_name + " WHERE idPessoa ="+prID;  
	    String[] selectionArgs = new String[] {"idPessoa"};  
	    Cursor cursor = this.dataBase.rawQuery(sql, null);
	    //verificando se existe dados
	    if(cursor.moveToNext()){
	    	entidade = new Pessoa();
	    	entidade.setIdPessoa(cursor.getInt(cursor.getColumnIndex(colunas[0])));
    		entidade.setNome(cursor.getString(cursor.getColumnIndex(colunas[1])));
    		entidade.setFoto(cursor.getString(cursor.getColumnIndex(colunas[2])));
    		entidade.setTelefone(cursor.getString(cursor.getColumnIndex(colunas[3])));
    		entidade.setEmail(cursor.getString(cursor.getColumnIndex(colunas[4])));
	    }
	    if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	    return entidade;
	}

}
