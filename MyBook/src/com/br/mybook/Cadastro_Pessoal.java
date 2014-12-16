package com.br.mybook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.br.adapter.PessoaAdapter;
import com.br.bybook.R;
import com.br.dao.PessoaDao;
import com.br.model.Pessoa;
import com.br.util.PopUp;
import com.br.util.ProcessaImagens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class Cadastro_Pessoal extends Activity implements OnClickListener,
		OnItemSelectedListener, OnItemLongClickListener {
	private Pessoa pessoa;
	private List<Pessoa> lista;
	private PessoaDao daoPessoa;
	private PessoaAdapter adapter;
	// atributos da view
	private EditText edtNome;
	private EditText edtTelefone;
	private EditText edtEmail;
	private ImageView imgView;
	private ListView listView;
	private Button btCadastrarPessoa;

	private Intent intent;
	
	// Obtém o local onde as fotos são armazenadas na memória externa do dispositivo
	
	private File imageFile;
	private static final int REQUEST_PICTURE = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cad_pessoa);
		// chamando metodo que inicia os objetos da tela
		initObjects();
		initDados();
		//File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		SimpleDateFormat formatoData = new SimpleDateFormat("dd-MM-yyy_hh-MM-ss");
		this.imageFile = new File(picsDir, formatoData.format(new Date())+"foto.jpg");
	}

	public void tirarFoto(View v) {
			// Cria uma intent que será usada para abrir a aplicação nativa de câmera
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				// Indica na intent o local onde a foto tirada deve ser armazenada
				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
				
				// Abre a aplicação de câmera
				startActivityForResult(i, REQUEST_PICTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try{
			// Verifica o código de requisição e se o resultado é OK (outro resultado indica que
			// o usuário cancelou a tirada da foto)
			if (requestCode == REQUEST_PICTURE && resultCode == RESULT_OK) {
				
				FileInputStream fis = null;
				
				try {
					try {
						// Cria um FileInputStream para ler a foto tirada pela câmera
						fis = new FileInputStream(imageFile);
						
						// Converte a stream em um objeto Bitmap
						Bitmap picture = BitmapFactory.decodeStream(fis);
						
						// Exibe o bitmap na view, para que o usuário veja a foto tirada
						imgView.setImageBitmap(picture);
					} finally {
						if (fis != null) {
							fis.close();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//metodo para procura suas imagens
	public String ProcuraImg(String imagem) {
		 String cm = null;    
		 File directory =null;
		 try {
	           

	           File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//camino do diretorio padrao

	             directory = new File(sdcard +"/"+ imagem);//tranforma em um file para ver si acha sua imagem
	            if (!directory.exists()) {
	                // Log.i("Diretorio Não encontrado caminho="+directory,
	                //return cm;
	            } 
	            else {
	                // Log.i("Diretorio encontrado encotrado="+directory, "Aviso");
	                cm = "" + directory;
	                //return cm;
	            }
	            
	           

	        }

	        catch (Exception e) {
	            Log.e("Erro criar caminho para icones e img empresas", "Erro");
	        }
		 	cm = "" + directory.getPath();
	        return  cm; 
	    }
	
	/************************** INICIA OS OBJETOS ******************/
	public void initObjects() {
		daoPessoa = new PessoaDao(this);
		pessoa = new Pessoa();
		// iniciando atributos da tela
		edtNome = (EditText) findViewById(R.id.editNomePessoa);
		edtTelefone = (EditText) findViewById(R.id.editTelefone);
		edtEmail = (EditText) findViewById(R.id.editEmail);
		listView = (ListView) findViewById(R.id.listViewCadPessoa);
		listView.setOnItemSelectedListener(this);
		listView.setOnItemLongClickListener(this);

		imgView = (ImageView)findViewById(R.id.foto_pessoa);
		
		// registrando listview para menu de context
		registerForContextMenu(listView);

		btCadastrarPessoa = (Button) findViewById(R.id.btCadPessoa);
		btCadastrarPessoa.setOnClickListener(this);

		// ocultar teclado
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edtNome.getWindowToken(), 0);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	public void initDados() {
		lista = daoPessoa.list();
		adapter = new PessoaAdapter(this, lista);
		listView.setAdapter(adapter);
	}

	/***
	 * Metodo de ação dos botoes
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == this.btCadastrarPessoa.getId()) {
			if (this.btCadastrarPessoa.getText().toString().equals("Cadastrar")) {
				//validacao
				if(edtNome.getText().toString().equals("")){
					throw new IllegalArgumentException("É Preciso Definir um Nome!");
				}
				// carregando atributos da tela para objeto				
				pessoa.setFoto(imageFile.getName());
				pessoa.setNome(edtNome.getText().toString());
				pessoa.setEmail(edtEmail.getText().toString());
				pessoa.setTelefone(edtTelefone.getText().toString());
				daoPessoa.save(pessoa);
				PopUp.showPopupWindow(v, "Cadastro Usuário", "Usuário Cadastrado com Sucesso", "OK");
			} else if (this.btCadastrarPessoa.getText().toString()
					.equals("Editar")) {
				pessoa.setFoto(imageFile.getName());
				pessoa.setEmail(edtEmail.getText().toString());
				pessoa.setNome(edtNome.getText().toString());
				pessoa.setTelefone(edtTelefone.getText().toString());
				daoPessoa.update(pessoa);
				btCadastrarPessoa.setText("Cadastrar");
				limparCampos();
				PopUp.showPopupWindow(v, "Cadastro Usuário", "Usuário Atualizado com Sucesso", "OK");
			}
		}
		initDados();
	}
	
	
	

	/**
	 * Carregando menu de context
	 * 
	 * @param menu
	 * @param v
	 * @param menuInfo
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_context, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.editar:
			chamarEdit();
			return true;
		case R.id.remover:
			chamarRemover();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	public void chamarEdit() {
		edtEmail.setText(pessoa.getTelefone().toString());
		edtNome.setText(pessoa.getNome().toString());
		edtTelefone.setText(pessoa.getTelefone().toString());
		String path = ProcuraImg(pessoa.getFoto());
		Bitmap btm = BitmapFactory.decodeFile(path);
		imgView.setImageBitmap(btm);
		btCadastrarPessoa.setText("Editar");
	}

	public void chamarRemover() {
		daoPessoa.delete(pessoa);
		initDados();
		PopUp.showPopupWindow(this.btCadastrarPessoa, "Cadastro Usuário", "Usuário Removido com Sucesso", "OK");
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		this.pessoa = (Pessoa) arg0.getSelectedItem();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		this.pessoa = (Pessoa) arg0.getItemAtPosition(arg2);
		return false;
	}

	public void limparCampos(){
		this.edtEmail.setText("");
		this.edtNome.setText("");
		this.edtTelefone.setText("");
	}
	
}