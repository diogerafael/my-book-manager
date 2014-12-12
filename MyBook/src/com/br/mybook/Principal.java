package com.br.mybook;



import com.br.bybook.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;


public class Principal extends Activity implements OnClickListener{
	
	 //variaves do menu
    private static final int iSair = 0;
    private static final int iSobre = 1;
	
	//declarando atributos
	private ImageButton imgCadPessoa;
	private ImageButton imgCadLivro;
	private ImageButton imgEmprestimo;
	private ImageButton imgDevolucao;
	
	private Intent intent;
	
	
	//_________________________CONTRUCTOR______________________________________
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.principal);
	        //chamando metodo que inicia os objetos da tela
	        initObjects();
	    }


	 /****************************************************
	     * Metodo para iniciar objetos
	     */
	 public void initObjects(){
		 //iniciando objetos
		 imgCadPessoa =(ImageButton)findViewById(com.br.bybook.R.id.imgBtCadPessoa);
		 imgCadPessoa.setOnClickListener(this);//registrando evento
		 
		 imgCadLivro =(ImageButton)findViewById(R.id.imgCadLivro);
		 imgCadLivro.setOnClickListener(this);
		 
		 imgEmprestimo=(ImageButton)findViewById(R.id.imgEmprestimo);
		 imgEmprestimo.setOnClickListener(this);
		 
		 imgDevolucao =(ImageButton)findViewById(R.id.imgDevolucao);
		 imgDevolucao.setOnClickListener(this);
		 
		 
	 }
	 

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == this.imgCadPessoa.getId()){
			intent = new Intent(this,Cadastro_Pessoal.class);
			startActivity(intent);
		}
		else if(v.getId() == this.imgCadLivro.getId()){
			intent = new Intent(this,Cadastro_Livro.class);
			startActivity(intent);
		}
		else if(v.getId() == this.imgEmprestimo.getId()){
			intent = new Intent(this,Emprestimo.class);
			startActivity(intent);
		}
		else if(v.getId() == this.imgDevolucao.getId()){
			intent = new Intent(this,Devolucao.class);
			startActivity(intent);
		}
	}
	
	

    
    /****************************************************
     * Metodo para Criar Menu da primeira Tela
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {      
            try{
            //cria o menu
            MenuItem menuSair = menu.add(iSair,0, 0, "Sair");
            MenuItem menuSobre = menu.add(iSair, 1, 0, "Sobre");
            MenuItem menuConfig = menu.add(iSair, 2, 0, "Configuração");
            }  catch (Exception e) {
                  e.printStackTrace();
                }
               return super.onCreateOptionsMenu(menu);
    }
   
    //o método abaixo é disparado quando o usuário clicar
        //em algum dos itens do menu
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            //de acordo com o item selecionado você executará
            //a função desejada
            switch (item.getItemId()) {
                case 0:    
                    finish();
                    break;
                case 1:    
                   chamaDialogSobreSistema();
                    break;
                case 2:    
                    Intent i= new Intent(this,Configuracao.class);
                	startActivity(i);
                     break;
            }
            return true;
        }    
        //final to tratamento de menu
        /******************************************************************/
        /***************************************************
         * Metodo: chama dialog que informa sobre o sistema
         */
        public void chamaDialogSobreSistema(){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Sobre");
            alertDialog.setMessage("Sistema Desenvolvido por Diógenes Rafael");                      
            alertDialog.show();
        }
        /**************************************************/

	
	
}
