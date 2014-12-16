package com.br.mybook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.br.adapter.LivroAdapter;
import com.br.bybook.R;
import com.br.dao.LivroDao;
import com.br.model.Livro;
import com.br.model.Pessoa;
import com.br.util.PopUp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class Cadastro_Livro extends Activity implements OnClickListener,OnItemLongClickListener{
	private EditText edtDescricao;
	private EditText edtSerie;
	private EditText edtQtd;
	private ImageView imgView;
	private Button btCadLivro;
	private ImageButton btTirarFoto;
	
	private ListView listView;
	
	private Livro livro;
	private List<Livro> lista;
	private LivroDao daoLivro;
	private LivroAdapter adaper;
	
	private Intent intent;
	
	// Obtém o local onde as fotos são armazenadas na memória externa do dispositivo
	
	private File imageFile;
	private static final int REQUEST_PICTURE = 1000;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cad_livro);
        //chamando metodo que inicia os objetos da tela 
        initObjects();
        initDados();
        
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
	
	private void initDados() {
		// TODO Auto-generated method stub
		livro = new Livro();
		daoLivro = new LivroDao(this);
		
		//carregando dados da lista
		lista = daoLivro.list();
		adaper = new LivroAdapter(this, lista);
		listView.setAdapter(adaper);
	}

	private void initObjects() {
		// TODO Auto-generated method stub
		edtDescricao =(EditText)findViewById(R.id.editTitulo);
		edtSerie =(EditText)findViewById(R.id.editSerie);
		//edtQtd   =(EditText)findViewById(R.id.ediExemplar);
		
		btCadLivro = (Button)findViewById(R.id.btCadLivro);
		btCadLivro.setOnClickListener(this);
		btTirarFoto = (ImageButton)findViewById(R.id.btfotoLivro);
		
		
		listView = (ListView)findViewById(R.id.listViewCadLivro);
		listView.setOnItemLongClickListener(this);
		registerForContextMenu(listView);
		imgView =(ImageView)findViewById(R.id.foto_livro);
		
		//ocultar teclado
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtDescricao.getWindowToken(), 0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
	}

	/**
     * Carregando menu de context
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }
    
	
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
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
	
    
    public void chamarEdit(){
        edtDescricao.setText(livro.getDescricao());
        edtSerie.setText(livro.getSerie());
        edtQtd.setText(String.valueOf(livro.getQuantidade()));
        String path = ProcuraImg(livro.getFoto());
        btCadLivro.setText("Editar");
        Bitmap btm = BitmapFactory.decodeFile(path);
        imgView.setImageBitmap(btm);
    }

    public void chamarRemover(){
        daoLivro.delete(livro);
        initDados();
        PopUp.showPopupWindow(this.btCadLivro, "Cadastro Livro", "Livro Removido com Sucesso", "OK");
    }
    
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		this.livro = (Livro)arg0.getItemAtPosition(arg2);
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==this.btCadLivro.getId()) {
			if (this.btCadLivro.getText().toString().equals("Cadastrar")) {
				//carregando atributos da tela para objeto
				if(! (this.imageFile.getName()==null)){					
				}
				livro.setFoto(this.imageFile.getName());//pegar caminho a ser salvo
				livro.setDescricao(edtDescricao.getText().toString());
				livro.setSerie(edtSerie.getText().toString());
				//livro.setQuantidade(Integer.parseInt(edtQtd.getText().toString()));
				daoLivro.save(livro);
				PopUp.showPopupWindow(v, "Cadastro Livro", "Livro Cadastrado com Sucesso", "OK");
			}
			else if	(this.btCadLivro.getText().toString().equals("Editar")) {
				livro.setFoto(this.imageFile.getName());//pegar caminho a ser salvo
				livro.setDescricao(edtDescricao.getText().toString());
				livro.setSerie(edtSerie.getText().toString());
				livro.setQuantidade(Integer.parseInt(edtQtd.getText().toString()));
				daoLivro.update(livro);
				PopUp.showPopupWindow(v, "Cadastro Livro", "Livro Atualizado com Sucesso", "OK");			
		}						
	}
		initDados();
	}

}
