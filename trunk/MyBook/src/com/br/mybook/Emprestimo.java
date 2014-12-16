package com.br.mybook;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.br.adapter.EmprestimoHasLivrosAdapter;
import com.br.adapter.LivroAdapter;
import com.br.adapter.PessoaAdapter;
import com.br.bybook.R;
import com.br.dao.EmprestimoDao;
import com.br.dao.Emprestimo_has_LivrosDao;
import com.br.dao.LivroDao;
import com.br.dao.PessoaDao;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.br.model.*;
import com.br.util.Mask;
import com.br.util.PopUp;

public class Emprestimo extends Activity implements OnClickListener,OnItemSelectedListener,OnItemLongClickListener{

	//declaracao de atributos
	private com.br.model.Emprestimo emprestimoLivro;
	private List<Livro> livros;
	private List<Livro> livrosEmprestados;
	private Livro livro;
	private Pessoa pessoa;
	private List<Pessoa> pessoas;
	private PessoaDao daoPessoa;
	private LivroDao daoLivro;
	private EmprestimoDao daoEmprestimo;
	private Emprestimo_has_LivrosDao daoEmprestimoHasLivro;
	
	private List<Emprestimo_has_Livros> listaModelEmprestimoHasLivros;
	private Emprestimo_has_Livros modelEmprestimoHasLivros;
	//atributos da tela
	private Spinner spinnerPessoa;
	private Spinner spinnerLivros;
	private EditText editDataDevolucao;// =(EditText)findViewById(R.id.editEmprestimo);
	
	
	//DatePicker datePicker;
	private Button btEmprestar;
	private Button btaddLivro;
	private ListView listViewEmprestimo;
	//adapter
	private PessoaAdapter adapterPessoa;
	private LivroAdapter adapterLivro,adapterLivroEmprestado;
	private EmprestimoHasLivrosAdapter adapterLivroHasEmprestimo;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emprestimo);
        //chamando metodo que inicia os objetos da tela 
        initObjects();
        //initDados();
    }

	private void initDados() {
		// TODO Auto-generated method stub
		//livro = new Livro();
		
	}

	private void initObjects(){
		// TODO Auto-generated method stub
		emprestimoLivro = new com.br.model.Emprestimo();
		
		spinnerLivros =(Spinner)findViewById(R.id.spinner_emprestimo_livro);
		spinnerPessoa =(Spinner)findViewById(R.id.spinner_emprestimo_pessoa);
		
		editDataDevolucao=(EditText)findViewById(R.id.editEmprestimo);
		//editDataDevolucao
		//datePicker = (DatePicker)findViewById(R.id.dateEmprestimo);
		
		//editDataDevolucao.addTextChangedListener(Mask.insert("##/##/####", editDataDevolucao));
		
		btEmprestar =(Button)findViewById(R.id.btEmprestimo);
		btEmprestar.setOnClickListener(this);
		
		btaddLivro =(Button)findViewById(R.id.btAddLivro);
		btaddLivro.setOnClickListener(this);
		
		listViewEmprestimo=(ListView)findViewById(R.id.listViewEmprestimo);//listViewEmprestimo
		registerForContextMenu(listViewEmprestimo);
		listViewEmprestimo.setOnItemLongClickListener(this);
		registerForContextMenu(listViewEmprestimo);
		
		/*********CARREGANDO LIVROS PARA O SPNER**/
		daoLivro = new LivroDao(this);
		livros = daoLivro.list();
		adapterLivro = new LivroAdapter(this, livros);
		spinnerLivros.setAdapter(adapterLivro);
		spinnerLivros.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				//emprestimoLivro.setPessoa((Pessoa)arg0.getSelectedItem());
				livro = (Livro)arg0.getSelectedItem();
				//emprestimoLivro.getListaLivro().add(livro);
				//livro = null;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

				
			}
		});

			
		
		daoPessoa = new PessoaDao(this);
		pessoas = daoPessoa.list();
		adapterPessoa = new PessoaAdapter(this, pessoas);
		spinnerPessoa.setAdapter(adapterPessoa);		
		spinnerPessoa.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				pessoa= (Pessoa)arg0.getSelectedItem();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		daoEmprestimo = new EmprestimoDao(this);
		listaModelEmprestimoHasLivros = new ArrayList<Emprestimo_has_Livros>();
		
		//ocultar teclado
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editDataDevolucao.getWindowToken(), 0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
        
        this.livrosEmprestados = new ArrayList<Livro>();
        modelEmprestimoHasLivros = new Emprestimo_has_Livros();
	}

	
	@Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.remover:
                chamarRemover();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
	
	
	private void chamarRemover() {
		// TODO Auto-generated method stub
		this.emprestimoLivro.getListaLivro().remove(this.livro);
		this.adapterLivro  = new LivroAdapter(this, this.emprestimoLivro.getListaLivro());
		listViewEmprestimo.setAdapter(adapterLivro);
	}

	/**
     * Carregando menu de context
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context_2, menu);
    }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == this.btaddLivro.getId()){
			//validar se já existe na lista
						
			 if(this.editDataDevolucao.getText().toString().equals("")){
				PopUp.showPopupWindow(v, "Emprestimo", "Inserir Data de Devolução", "OK");
			}
			else{
				modelEmprestimoHasLivros.setLivro(this.livro);
				//modelEmprestimoHasLivros.setEmprestimo(this.emprestimoLivro);
				String data = editDataDevolucao.getText().toString();
				SimpleDateFormat format  = new SimpleDateFormat("dd/MM/yyyy");
				try {
					if(format.parse(data).before(new Date())){
						PopUp.showPopupWindow(v, "Emprestimo", "Data de Devolução Inferior da Data de Emprestimo", "OK");
						return;
					}
					modelEmprestimoHasLivros.setDataDevolucao(format.parse(data));
				} catch (ParseException e1){
					PopUp.showPopupWindow(v, "Emprestimo", "Inserir Data no Padrão dd/MM/yyyy", "OK");
					return;
				}
				modelEmprestimoHasLivros.setStatus(1);
				listaModelEmprestimoHasLivros.add(modelEmprestimoHasLivros);
				adapterLivroHasEmprestimo = new EmprestimoHasLivrosAdapter(this, listaModelEmprestimoHasLivros);
				listViewEmprestimo.setAdapter(adapterLivroHasEmprestimo);				
			}							
		}
		else if(v.getId() == btEmprestar.getId()){
			if(listaModelEmprestimoHasLivros.size() <=0){
				PopUp.showPopupWindow(v, "Emprestimo", "Inserir Livros no Emprestimo!", "OK");
			}else if(listaModelEmprestimoHasLivros == null){
				PopUp.showPopupWindow(v, "Emprestimo", "Definir Livro", "OK");
			}
			else{
				//inserindod dados do emprestimo
				
				emprestimoLivro.setData(new Date());
				emprestimoLivro.setPessoa(this.pessoa);
				emprestimoLivro.setStatus(1);
				
				
				emprestimoLivro.setIdEmprestimo((int)daoEmprestimo.save(emprestimoLivro));
				daoEmprestimoHasLivro = new Emprestimo_has_LivrosDao(this);				
				for (int i = 0; i < listaModelEmprestimoHasLivros.size(); i++) {
					listaModelEmprestimoHasLivros.get(i).setEmprestimo(this.emprestimoLivro);
					daoEmprestimoHasLivro.save(listaModelEmprestimoHasLivros.get(i));
				}
				listaModelEmprestimoHasLivros = new ArrayList<Emprestimo_has_Livros>();
				listViewEmprestimo.setAdapter(null);
			}
			
			PopUp.showPopupWindow(v, "Emprestimo", "Emprestimo Efetuado com Sucesso", "OK");
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View v, int arg2,
			long arg3) {

}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		this.livro = (Livro)arg0.getItemAtPosition(arg2);
		return false;
	}	
}