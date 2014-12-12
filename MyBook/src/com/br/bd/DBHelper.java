package com.br.bd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

	private final Context context;
	private static final String DATABASE_NAME = "mybook";
	private static final int DATABASE_VERSION = 2;
	private static final String TAG = "Todos";
	 
	
	public DBHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;


	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		//carregaDB();
		criarBanco(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		// TODO Auto-generated method stub
		
	}


	/*********************************************************
	 * Mtodo:criar Banco, ele verifica se o banco existe e cria um novo
	 * @param db
	 */
	public void criarBanco(SQLiteDatabase db){	
		StringBuilder sql = new StringBuilder();
		sql.append(" CREATE TABLE [Emprestimo] ( ");
		sql.append("[idEmprestimo] INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT,"); 
		sql.append("[data] DATE,"); 
		sql.append("[idPessoa] INTEGER,"); 
		sql.append("[status] INTEGER);");
		
		db.execSQL(sql.toString());
		sql = new StringBuilder();
		
		sql.append("CREATE TABLE [Emprestimo_has_Livro] (");
		sql.append("[idLivro] INTEGER,"); 
		sql.append("[idEmprestimo] INTEGER,"); 
		sql.append("[dataDevolucao] DATE,");
		sql.append("[dataDevolvido] DATE,");
		sql.append("[status] INTEGER);");

		db.execSQL(sql.toString());
		sql = new StringBuilder();
		
		sql.append("CREATE TABLE [Livro] (");
		sql.append("[idLivro] INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT,"); 
		sql.append("[descricao] VARCHAR NOT NULL,"); 
		sql.append("  [foto] varchar(50), ");
		sql.append("  [serie] VARCHAR(15),"); 
		sql.append("  [quantidade] INTEGER);");

		db.execSQL(sql.toString());
		sql = new StringBuilder();
		
		sql.append("CREATE TABLE [Pessoa] ( ");
		sql.append("[idPessoa] INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT,"); 
		sql.append("[nome] VARCHAR(50), ");
		sql.append("[foto] varchar(50), ");
		sql.append("[email] varchar(50), ");
		sql.append("[telefone] varchar(14));");	
		
		db.execSQL(sql.toString());
		sql = new StringBuilder();
		
		sql.append("CREATE TABLE [Config] (");
		sql.append("[idConfig] INTEGER PRIMARY KEY AUTOINCREMENT,"); 
		sql.append("[enviar_email] INT,"); 
		sql.append("[conteudo_email] VARCHAR,"); 
		sql.append("[conteudo_sms] VARCHAR,");
		sql.append("[diasEmprestimo] INTEGER,"); 
		sql.append("[enviar_sms] INT);");
		
		db.execSQL(sql.toString());
		sql = new StringBuilder();
		
		sql.append("INSERT INTO Pessoa (nome,foto,telefone) values ('Pessoa','caminho_foto','11358888')");
		db.execSQL(sql.toString());

    }
	
	@SuppressLint("SdCardPath")
	public void carregaDB(){
		//Carregando o database
		try{
			String destPath = "/data/data/"+ context.getPackageName() + "/databases/"+DATABASE_NAME;
			File f = new File(destPath);
			if(!f.exists()){
				//copyDB(context.getAssets().open("onyx.sqlite"), new FileOutputStream(destPath));
				copiarDataBase();
			}
		}
		catch (IOException  e){
			Log.v("Erro",e.getMessage());
		}		
	}

	public void copyDB(InputStream inputStream, OutputStream outputStream) throws IOException{
		byte[] buffer = new byte[1024];
		int lenght;
		while((lenght = inputStream.read(buffer)) > 0)
		{
			outputStream.write(buffer, 0, lenght);
		}
		inputStream.close();
		outputStream.close();
	}
	
	
	private void copiarDataBase() throws IOException{
	 
	      InputStream myInput = this.context.getAssets().open(DATABASE_NAME);
	      //String outFileName = D + DB_NAME;
	      String destPath = "/data/data/"+ context.getPackageName() + "/databases/"+DATABASE_NAME;
	      OutputStream myOutput = new FileOutputStream(destPath);
	 
	      byte[] buffer = new byte[1024];
	      int length;
	      while ((length = myInput.read(buffer))>0)
	{
	            myOutput.write(buffer, 0, length);
	      }
	 
	      myOutput.flush();
	      myOutput.close();
	      myInput.close();
	 
	}
	
}