package com.br.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.br.bd.DBHelper;
import com.br.model.Emprestimo;
import com.br.model.Emprestimo_has_Livros;
import com.br.model.Pessoa;

public class EmprestimoDao extends AbstractDao implements InterfaceDao<Emprestimo>{
	private PessoaDao daoPessoa;
	private Context context;
	private String table_name = "Emprestimo";
	private String table_name2 = "Emprestimo_has_Livro";
    private String[] colunas = new String[] { "idEmprestimo", "data","idPessoa","status"};
    private String[] colunas2 = new String[] { "idLivro", "idEmprestimo","dataDevolucao"};
	private Long ultimoID;
    private Emprestimo_has_LivrosDao daoEmprestimoHasLivroDao;
    private Emprestimo_has_Livros modeloEmprestimoHasLivro;
  //_____________________constructor_______________
    public EmprestimoDao(Context context){
        openHelper   =  new DBHelper(context);
        this.context = context;
    }
    
	@Override
	public long save(Emprestimo entidade) {
		// TODO Auto-generated method stub
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
            open();
            ContentValues values = new ContentValues();
            values.put(colunas[2], entidade.getPessoa().getIdPessoa());
            values.put(colunas[1], new Date().getTime());
            values.put(colunas[3], entidade.getStatus());
            ultimoID= this.dataBase.insert("Emprestimo", null, values);
            entidade.setIdEmprestimo( (int) (long)ultimoID);
            return entidade.getIdEmprestimo();
        }catch (Exception e){
            e.printStackTrace();
        }
		return -1;
	}

	@Override
	public long update(Emprestimo entidade) {
		// TODO Auto-generated method stub
	return -1;	
	}

	@Override
	public long delete(Emprestimo entidade) {
		// TODO Auto-generated method stub
		try{
            open();
            String filter = "idEmprestimo="+entidade.getIdEmprestimo();
           return this.dataBase.delete(table_name,filter,null);
        }catch (Exception e){
            e.printStackTrace();
        }
		return -1;
	}

	@Override
	public List<Emprestimo> list() {
		daoPessoa = new PessoaDao(context);		
		List<Emprestimo> lista = new ArrayList<Emprestimo>();
		try{//lendo redos da database
			this.dataBase = this.openHelper.getReadableDatabase();
			Cursor cursor = this.dataBase.query(table_name,colunas,null, null, null, null, null);
			if (cursor.moveToFirst()) {
                do {
                    Emprestimo entidade = new Emprestimo();
                            entidade.setIdEmprestimo(cursor.getInt(cursor.getColumnIndex(colunas[0])));
                            long data = cursor.getLong(cursor.getColumnIndex(colunas[1]));
                            entidade.setData(new Date(data));
                    		entidade.setPessoa(daoPessoa.getEntidade(cursor.getInt(cursor.getColumnIndex(colunas[2]))));             		
                    		entidade.setStatus(cursor.getString(cursor.getColumnIndex(colunas[3])).charAt(0));                    		
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
	public Emprestimo getEntidade(int prID) {
		Emprestimo entidade =null;
		daoPessoa = new PessoaDao(context);				
		try{//lendo redos da database
			this.dataBase = this.openHelper.getReadableDatabase();
			Cursor cursor = this.dataBase.query(table_name,colunas,null, null, null, null, null);
			if (cursor.moveToFirst()) {               
							entidade = new Emprestimo();
                            entidade.setIdEmprestimo(cursor.getInt(cursor.getColumnIndex(colunas[0])));
                    		//verificar
                            //entidade.setData(cursor.get (cursor.getColumnIndex(colunas[1])));
                    		//entidade.getPessoa().setIdPessoa(cursor.getInt(cursor.getColumnIndex(colunas[2])));
                    		entidade.setPessoa(daoPessoa.getEntidade(cursor.getInt(cursor.getColumnIndex(colunas[2]))));
                            //                   		
                    		entidade.setStatus(cursor.getString(cursor.getColumnIndex(colunas[3])).charAt(0));                    		                    

                }
            
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }            
	
		}catch(Exception e){
			e.printStackTrace();
		}
		return entidade;
	}
	
	
	public List<Emprestimo> list(int prIdPessoa) {
		daoPessoa = new PessoaDao(context);		
		List<Emprestimo> lista = new ArrayList<Emprestimo>();
		try{//lendo redos da database
			this.dataBase = this.openHelper.getReadableDatabase();
			String sql = "SELECT * FROM " + table_name + " WHERE idPessoa = "+prIdPessoa +" and status=1";  
		    String[] selectionArgs = new String[] {"idPessoa"};  
		    Cursor cursor = this.dataBase.rawQuery(sql, null);
			if (cursor.moveToNext()) {
                do {
                    		Emprestimo entidade = new Emprestimo();
                            entidade.setIdEmprestimo(cursor.getInt(cursor.getColumnIndex(colunas[0])));
                            entidade.setIdEmprestimo(cursor.getInt(cursor.getColumnIndex(colunas[0])));
                            long data = cursor.getLong(cursor.getColumnIndex(colunas[1]));
                            entidade.setData(new Date(data));
                    		entidade.setPessoa(daoPessoa.getEntidade(cursor.getInt(cursor.getColumnIndex(colunas[2]))));             		
                    		entidade.setStatus(cursor.getInt(cursor.getColumnIndex(colunas[3])));                    		                   		
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

	//metodo que muda o status de um livro de um emprestimo
	public void updateStatusLivoEmprestimo(int prIdLivro,int prIdEmprestimo){
		
	}
	
public void deleteLivoEmprestimo(int prIdLivro,int prIdEmprestimo,int status){
		try{
			open();
			String filter =  "idLivro="+prIdLivro +" and idEmprestimo="+prIdEmprestimo;
			 ContentValues values = new ContentValues();
	            values.put(colunas[3],status);	         
			this.dataBase.update("Emprestimo_has_Livro",values,filter,null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}	

public long updateEmprestimo(int prIdEmprestimo){
	open();
	String filter =  " idEmprestimo="+prIdEmprestimo;
	ContentValues values = new ContentValues();
	values.put("status", 0);
	return this.dataBase.update(table_name, values, filter, null);
	//return -1;
}

}
