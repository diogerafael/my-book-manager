package com.br.mybook;

import com.br.bybook.R;
import com.br.dao.ConfigDao;
import com.br.model.Config;
import com.br.util.PopUp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Configuracao extends Activity implements OnClickListener{
	//declaracao de atributos de tela
	private CheckBox chkEmail;
	private CheckBox chkSms;
	private Button btExecutar;
	private EditText edtTexto;
	
	// modelos
	private Config modelo = null;
	
	
	//Dao
	private ConfigDao daoConfig;
	
	
	
	//_________________________CONTRUCTOR______________________________________
	@Override
	protected void onCreate(Bundle b){
		super.onCreate(b);
        setContentView(R.layout.config);	
        initObjectsView();
        initObjects();
	}
	
	
	/*
	 * Metodo que inicia os objetos da tela
	 */
	public void initObjectsView(){
		chkEmail = (CheckBox)findViewById(R.id.chkEMail);
		chkSms = (CheckBox)findViewById(R.id.chSms);
		
		btExecutar = (Button)findViewById(R.id.btExecutar);
		btExecutar.setOnClickListener(this);
		
		edtTexto = (EditText)findViewById(R.id.editTextoConfig);
		
		
	}
	
	public void initObjects(){
		daoConfig = new ConfigDao(this);
		modelo = daoConfig.getEntidade();
		if(modelo.getIdConfig()!=null){
			edtTexto.setText(modelo.getConteudoEmail());
			if(modelo.getEnviarEmail()==1){
				chkEmail.setChecked(true);
			}
			if(modelo.getEnviarSms()==1){
				chkSms.setChecked(true);
			}
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == this.btExecutar.getId()){
			if(modelo.getIdConfig() ==null){
				modelo.setConteudoEmail(edtTexto.getText().toString());
				daoConfig.save(modelo);
				PopUp.showPopupWindow(chkSms, "Configuração", "teste", "Ok");
			}else{
				daoConfig.update(modelo);
				PopUp.showPopupWindow(chkSms, "Configuração", "teste", "Ok");
			}
		}
	}
	
	
	public void onCheckboxClicked(View view) {
	    // Is the view now checked?
	    boolean checked = ((CheckBox) view).isChecked();
	    
	    // Check which checkbox was clicked
	    switch(view.getId()) {
	        case R.id.chkEMail:
	            if (checked)
	                // Put some meat on the sandwich
	            	modelo.setEnviarEmail(1);
	            else
	                // Remove the meat
	            	modelo.setEnviarEmail(0);
	            break;
	        case R.id.chSms:
	            if (checked)
	                // Cheese me
	            	modelo.setEnviarSms(1);
	            else
	                // I'm lactose intolerant
	            	modelo.setEnviarSms(1);
	            break;
	        // TODO: Veggie sandwich
	    }
	}
	
}
