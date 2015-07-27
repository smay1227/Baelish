package com.darna.wmxfx.atys;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.fragment.Frg_Dish;
import com.darna.wmxfx.fragment.Frg_ShopComment;
import com.darna.wmxfx.fragment.Frg_ShopInfo;

public class Aty_dish extends FragmentActivity {
	private FragmentTabHost mTabHost = null;
	private int iTabIndex = 0;
	String shopName;
	ImageView iv_rleft;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_dish);
		iv_rleft = (ImageView) findViewById(R.id.iv_rleft);
		shopName = getIntent().getStringExtra(Config.KEY_SHOPNAME);
		//shopId = getIntent().getStringExtra(Config.KEY_SHOPID);
		initHead();
		initTab();
		
		iv_rleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void initHead() {
		TextView tv_shopname = (TextView) findViewById(R.id.tv_shopname);
		tv_shopname.setText(shopName);
 	}

	private void initTab() {
		String[] strTab = getResources().getStringArray(R.array.dish_tab);
		RelativeLayout[] relativeLayouts = new RelativeLayout[strTab.length];
		for (int i = 0; i < strTab.length; i++) {
			relativeLayouts[i] = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_dish, null);
			TextView tv_tab_dish = (TextView) relativeLayouts[i].findViewById(R.id.tv_tab_dish);
			tv_tab_dish.setText(strTab[i]);
			if (i == 0) {
				tv_tab_dish.setTextColor(getResources().getColor(R.color.textused));
			}else {
				tv_tab_dish.setTextColor(getResources().getColor(R.color.textunused));
			}
		}
		
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
		
		mTabHost.addTab(mTabHost.newTabSpec(Config.KEY_INDEX)  
                .setIndicator(relativeLayouts[0]), Frg_Dish.class, null); 
        
        mTabHost.addTab(mTabHost.newTabSpec(Config.KEY_CART)  
                .setIndicator(relativeLayouts[1]), Frg_ShopComment.class, null);
        
        mTabHost.addTab(mTabHost.newTabSpec(Config.KEY_USERCENTER)  
                .setIndicator(relativeLayouts[2]), Frg_ShopInfo.class, null);
        
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				View v = mTabHost.getTabWidget().getChildAt(iTabIndex);
				((TextView) v.findViewById(R.id.tv_tab_dish)).setTextColor(getResources().getColor(R.color.textunused));
				v = mTabHost.getCurrentTabView();
				iTabIndex = mTabHost.getCurrentTab();
				((TextView) v.findViewById(R.id.tv_tab_dish)).setTextColor(getResources().getColor(R.color.textused));
			}
		});
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
