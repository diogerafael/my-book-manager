package com.br.mybook;

import java.util.List;

import com.br.adapter.EmprestimoAdapter;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.br.model.*;

public class Devolucao extends Activity implements OnItemLongClickListener,OnItemSelectedListener{
	private List<com.br.model.Emprestimo> listaEmprestimo;
	private List<Pessoa> pessoas;
	private List<Livro> livrosEmprestados;
	private PessoaDao daoPessoa;
	private PessoaAdapter adapterPessoa;
	private com.br.model.Emprestimo       emprestimo;
	private com.br.model.Pessoa		  pessoa;
	private EmprestimoDao    daoEmprestimo;
	private LivroDao 		 daoLivro;
	private Livro 			 livro =null;
	private EmprestimoAdapter adapterEmprestimo;
	private Spinner  spinnerPessoa;
	private Spinner  spinnerEmprestimo;
	private ListView listViewLivro;
	private LivroAdapter adapterLivro;
	private Emprestimo_has_Livros emprestimoHasLivro;
	private List<Emprestimo_has_Livros> listaEmprestimoHasLivro;
	private Emprestimo_has_LivrosDao daoEmprestimoHasLivro ;///= new Emprestimo_has_LivrosDao(getApplicationContext());
	private EmprestimoHasLivrosAdapter adapterEmprestimoHasLivro;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devolucao);
        //chamando metodo que inicia os objetos da tela 
        	initObjects();
        	initDados();
    }

	private void initDados() {
		// TODO Auto-generated method stub
		//listaEmprestimo = daoEmprestimo.list();
		pessoas = daoPessoa.list();
		adapterPessoa = new PessoaAdapter(this, pessoas);
		spinnerPessoa.setAdapter(adapterPessoa);
		
		
		daoEmprestimoHasLivro = new Emprestimo_has_LivrosDao(this);
		
	}

	private void initObjects() {
		// TODO Auto-generated method stub
		//iniciando objetos
		daoLivro = new LivroDao(this);
		daoEmprestimo = new EmprestimoDao(this);
		
		daoPessoa = new PessoaDao(this);
		pessoa = new Pessoa();
		
		//iniciando objetos da tela
		spinnerPessoa = (Spinner)findViewById(R.id.spinner_devolucao_pessoa);
		spinnerEmprestimo = (Spinner)findViewById(R.id.spinner_devolucao_emprestimo);
		spinnerPessoa.setOnItemSelectedListener(this);
		spinnerEmprestimo.setOnItemSelectedListener(this);
		
		
		//lista de livros emprados
		listViewLivro=(ListView)findViewById(R.id.listView_emprestados);
		listViewLivro.setOnItemLongClickListener(this);
		registerForContextMenu(listViewLivro);
		
		
	}
	
	
	/***********************MENU DE CONTEXT************************/
	 @Override
	    public void onCreateContextMenu(ContextMenu menu, View v,
	                                    ContextMenu.ContextMenuInfo menuInfo) {
	        super.onCreateContextMenu(menu, v, menuInfo);
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.menu_context_2, menu);
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
	    	this.listaEmprestimoHasLivro.remove(this.emprestimoHasLivro);
	    	//mudar o status na tabela
	    	daoEmprestimo.deleteLivoEmprestimo(this.emprestimoHasLivro.getLivro().getIdLivro(),this.emprestimoHasLivro.getEmprestimo().getIdEmprestimo(),0);
	    	//carregando lista novamente
	    	//verificando se a lista veio vazia e limpando o emprestimo da tela de devolução
	    	
	    	if(this.listaEmprestimoHasLivro.size()<=0){
	    		this.daoEmprestimo.updateEmprestimo(this.emprestimo.getIdEmprestimo());
	    		listaEmprestimoHasLivro = null;
	    		listViewLivro.setAdapter(null);
	    		return;
	    	}
	    	
	    	listaEmprestimoHasLivro = daoEmprestimoHasLivro.list(this.pessoa.getIdPessoa(), this.emprestimo.getIdEmprestimo());
			adapterEmprestimoHasLivro = new EmprestimoHasLivrosAdapter(this, listaEmprestimoHasLivro);
			listViewLivro.setAdapter(adapterEmprestimoHasLivro);
	    	
			
		}

		/****************************************************************/

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		this.emprestimoHasLivro = (Emprestimo_has_Livros)arg0.getItemAtPosition(arg2);
		return false;
	}

		@Override
		public void onItemSelected(AdapterView<?> adapter, View v, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if(adapter.getId() == this.spinnerPessoa.getId()){
				this.pessoa = (Pessoa)adapter.getSelectedItem();	
				//montar a lista de emprestimos da pessoa
				listaEmprestimo =  daoEmprestimo.list(pessoa.getIdPessoa());
				adapterEmprestimo = new EmprestimoAdapter(this, listaEmprestimo);
				spinnerEmprestimo.setAdapter(adapterEmprestimo);				
			}
			if(adapter.getId() == this.spinnerEmprestimo.getId()){
				this.emprestimo =  (com.br.model.Emprestimo)adapter.getSelectedItem();
				//livrosEmprestados = daoLivro.listEmprestadosByEmprestimo(this.emprestimo.getIdEmprestimo());
				//adapterLivro = new LivroAdapter(this, livrosEmprestados);
				
				//carregar emprestimoHasLivro
				listaEmprestimoHasLivro = daoEmprestimoHasLivro.list(this.pessoa.getIdPessoa(), this.emprestimo.getIdEmprestimo());
				adapterEmprestimoHasLivro = new EmprestimoHasLivrosAdapter(this, listaEmprestimoHasLivro);
				listViewLivro.setAdapter(adapterEmprestimoHasLivro);
			}
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}

}