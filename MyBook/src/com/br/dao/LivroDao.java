package com.br.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.br.bd.DBHelper;
import com.br.model.Emprestimo;
import com.br.model.Livro;

public class LivroDao extends AbstractDao implements InterfaceDao<Livro> {

	private Context context;
	private String table_name = "Livro";
    private String[] colunas = new String[] { "idLivro", "descricao","foto","serie","quantidade" }; 
	
    
    public LivroDao(Context ctx){
    	openHelper = new DBHelper(ctx);
    	this.context = ctx;
    }
    
	@Override
	public long save(Livro entidade) {
		open();//abrindo conexao com banco
		ContentValues values = new ContentValues();
		values.put("descricao", entidade.getDescricao());
		values.put("foto", entidade.getFoto());
		values.put("serie", entidade.getSerie());
		values.put("quantidade", entidade.getQuantidade());
		//gravando dados no banco
		return this.dataBase.insert(table_name, null, values);
	}

	@Override
	public long update(Livro entidade) {
		// 
		open();
		String filter = "idLivro="+entidade.getIdLivro();
        ContentValues values = new ContentValues();
        values.put("descricao", entidade.getDescricao());
		values.put("foto", entidade.getFoto());
		values.put("serie", entidade.getSerie());
		values.put("quantidade", entidade.getQuantidade());
		return this.dataBase.update(table_name,values,filter,null);
	}

	@Override
	public long delete(Livro entidade) {
		// TODO Auto-generated method stub
		open();
        String filter = "idLivro="+entidade.getIdLivro();
        return this.dataBase.delete(table_name,filter,null);
	}

	/*******
	 * Metodo que Retorna a Lista de Livros
	 */
	@Override
	public List<Livro> list() {
		List<Livro> lista = new ArrayList<Livro>();
		try{//lendo redos da database
			this.dataBase = this.openHelper.getReadableDatabase();
			Cursor cursor = this.dataBase.query("Livro", new String[]{"idLivro", "descricao","foto","serie","quantidade"},
                    null, null, null, null, "descricao desc");
			if (cursor.moveToFirst()) {
                do {
                    Livro entidade = new Livro();
                            entidade.setIdLivro(cursor.getInt(cursor.getColumnIndex("idLivro")));
                    		entidade.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                    		entidade.setFoto(cursor.getString(cursor.getColumnIndex("foto")));
                    		entidade.setSerie(cursor.getString(cursor.getColumnIndex("serie")));
                    		entidade.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));
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
	/**
	 * Metodo:
	 * Retorna 
	 */
	@Override
	public Livro getEntidade(int prID) {
		Livro entidade =null;
		try{//lendo redos da database
			this.dataBase = this.openHelper.getReadableDatabase();
			Cursor cursor = this.dataBase.query("Livro", new String[]{"idLivro", "descricao","foto","serie","quantidade"},
                    null, null, null, null, "descricao desc");
			if (cursor.moveToNext()) {
              
                     entidade = new Livro();
                            entidade.setIdLivro(cursor.getInt(cursor.getColumnIndex("idLivro")));
                    		entidade.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                    		entidade.setFoto(cursor.getString(cursor.getColumnIndex("foto")));
                    		entidade.setSerie(cursor.getString(cursor.getColumnIndex("serie")));
  
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return entidade;
	}

	
	public List<Livro> listEmprestadosByEmprestimo(int prIdEmprestimo) {
		//daoPessoa = new PessoaDao(context);		
		List<Livro> lista = new ArrayList<Livro>();
		LivroDao daoLivro = new LivroDao(context);
		String sql = "SELECT * FROM Emprestimo_has_Livro   WHERE idEmprestimo = "+prIdEmprestimo +" and status=1 "  ;  
		try{//lendo redos da database
			this.dataBase = this.openHelper.getReadableDatabase();
			//Cursor cursor = this.dataBase.query(table_name,colunas,null, null, null, null, null);
			Cursor cursor = this.dataBase.rawQuery(sql, null);
			if (cursor.moveToFirst()){
                do {
                    		Livro entidade = new Livro();
                            entidade = daoLivro.getEntidade(cursor.getInt(cursor.getColumnIndex("idLivro")));
                    		//entidade.setIdEmprestimo(cursor.getInt(cursor.getColumnIndex(colunas[0])));
                    		//verificar
                            //entidade.setData(cursor.get (cursor.getColumnIndex(colunas[1])));
                    		//entidade.getPessoa().setIdPessoa(cursor.getInt(cursor.getColumnIndex(colunas[2])));
                    		//entidade.setPessoa(daoPessoa.getEntidade(cursor.getInt(cursor.getColumnIndex(colunas[2]))));
                                                		
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
	
}
