package com.darna.wmxfx.atys;

import com.baidu.mapapi.SDKInitializer;
import com.darna.wmxfx.R;
import com.darna.wmxfx.fragment.Frg_LocationHistory;
import com.darna.wmxfx.fragment.Frg_LocationNow;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Aty_Location extends FragmentActivity  {
	
	EditText et_searchkey;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.aty_location);
		et_searchkey = (EditText) findViewById(R.id.et_searchkey);
		
		Frg_LocationHistory locationHistory = new Frg_LocationHistory();
		getSupportFragmentManager().beginTransaction().replace(R.id.ll_frg, locationHistory).commit();
		
		et_searchkey.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() <= 0) {
					Frg_LocationHistory locationHistory = new Frg_LocationHistory();
					getSupportFragmentManager().beginTransaction().replace(R.id.ll_frg, locationHistory).commit();
				}else {
					Frg_LocationNow locationNow = new Frg_LocationNow();
					getSupportFragmentManager().beginTransaction().replace(R.id.ll_frg, locationNow).commit();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
	}
}

























