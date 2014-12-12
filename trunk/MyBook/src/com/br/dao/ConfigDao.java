package com.br.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.br.bd.DBHelper;
import com.br.model.Config;

public class ConfigDao extends AbstractDao implements InterfaceDao<Config>{

	private Context context;
	private String table_name = "Config";
    private String[] colunas = new String[] {"idConfig", "enviar_email","conteudo_email","conteudo_sms","enviar_sms","diasEmprestimo"};
	
	
    public  ConfigDao(Context ctx){
    	openHelper = new DBHelper(ctx);
    	this.context = ctx;
    }
	
	
	@Override
	public long save(Config entidade) {
		open();
		ContentValues values  = new ContentValues();
		values.put(colunas[1], entidade.getEnviarEmail());
		values.put(colunas[2], entidade.getConteudoEmail());
		values.put(colunas[3], entidade.getEnviarSms());
		values.put(colunas[4], entidade.getConteudoSms());
		values.put(colunas[5], entidade.getDiasEmprestimo());
		return this.dataBase.insert(table_name, null, values);
		
	}

	@Override
	public long update(Config entidade) {
		open();
		String filter = "idConfig="+entidade.getIdConfig();
		ContentValues values  = new ContentValues();
		values.put(colunas[1], entidade.getEnviarEmail());
		values.put(colunas[2], entidade.getConteudoEmail());
		values.put(colunas[3], entidade.getEnviarSms());
		values.put(colunas[4], entidade.getConteudoSms());
		values.put(colunas[5], entidade.getDiasEmprestimo());
	return this.dataBase.update(table_name,values,filter,null);
		
	}

	@Override
	public long delete(Config entidade) {
		// TODO Auto-generated method stub
		open();
		String filter = "idPessoa="+entidade.getIdConfig();
        return this.dataBase.delete(table_name,filter,null);
	}

	@Override
	public List<Config> list() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Config getEntidade() {
		// TODO Auto-generated method stub
		Config modelo = new Config();
		try{
			this.dataBase = this.openHelper.getReadableDatabase();		
			String sql = "SELECT * FROM " + table_name ;
			Cursor cursor = this.dataBase.rawQuery(sql, null);
			
			if(cursor.moveToFirst()){
				modelo.setIdConfig(cursor.getInt(cursor.getColumnIndex("idConfig")));
				modelo.setConteudoEmail(cursor.getString(cursor.getColumnIndex("conteudo_email")));
				modelo.setConteudoSms(cursor.getString(cursor.getColumnIndex("conteudo_sms")));
				modelo.setEnviarEmail(cursor.getInt(cursor.getColumnIndex("enviar_email")));
				modelo.setEnviarSms(cursor.getInt(cursor.getColumnIndex("enviar_sms")));
				modelo.setDiasEmprestimo(cursor.getInt(cursor.getColumnIndex("diasEmprestimo")));
			}
			if(cursor!= null && cursor.isClosed()){
				cursor.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return modelo;
	}


	@Override
	public Config getEntidade(int prID) {
		// TODO Auto-generated method stub
		return null;
	}

}
