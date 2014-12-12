package com.br.util;

import com.br.bybook.R.layout;

import android.R;
import android.content.Context;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopUp {
	public static void showPopupWindow(View view,String title,String message,String btLabel){
		//
		final PopupWindow popupWindow = new PopupWindow(view.getContext());
		//
		LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//
		LinearLayout linearLayout = (LinearLayout)inflater.inflate(com.br.bybook.R.layout.popup_window, null);
		
		TextView tv =(TextView)linearLayout.findViewById(com.br.bybook.R.id.tvTitle);
		tv.setText(title);
		
		tv =(TextView)linearLayout.findViewById(com.br.bybook.R.id.tvInfo);
		tv.setText(message);
		
		Button bt = (Button)linearLayout.findViewById(com.br.bybook.R.id.btOk);
		bt.setText(btLabel);
		bt.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				
			}
		});	
		
		popupWindow.setContentView(linearLayout);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		popupWindow.setWidth(LayoutParams.MATCH_PARENT);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(view.getContext().getResources().getDrawable(android.R.color.transparent));
		//popupWindow.showAsDropDown(view,0,0);
		//exibindo no centro da tela
		popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	}
}
