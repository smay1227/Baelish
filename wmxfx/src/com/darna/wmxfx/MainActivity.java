package com.darna.wmxfx;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.darna.wmxfx.fragment.Frg_Cart;
import com.darna.wmxfx.fragment.Frg_Index;
import com.darna.wmxfx.fragment.Frg_UserCenter;


public class MainActivity extends FragmentActivity {

	private FragmentTabHost mTabHost = null;
	private int iTabIndex = 0;
	private int[][] iTab = new int[][] { { R.drawable.indexunused, R.drawable.cartunused, R.drawable.usercenterunused },
			{ R.drawable.indexused, R.drawable.cartused, R.drawable.usercenterused } };
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    
    private void initViews(){
    	String[] strTab = getResources().getStringArray(R.array.bottom_tab);
    	RelativeLayout[] relativeLayout = new RelativeLayout[strTab.length];
    	for(int i = 0; i < strTab.length; i++){
    		if (i == 0) {
    			relativeLayout[i] = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_bottom, null);
    			relativeLayout[i].findViewById(R.id.iv_bottom_tab).setBackgroundResource(iTab[1][i]);
    			TextView tView =  (TextView) relativeLayout[i].findViewById(R.id.tv_bottom_tab);
    			tView.setText(strTab[i]);
    			tView.setTextColor(getResources().getColor(R.color.textused));
			}else {
				relativeLayout[i] = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tab_bottom, null);
    			relativeLayout[i].findViewById(R.id.iv_bottom_tab).setBackgroundResource(iTab[0][i]);
    			TextView t =  (TextView) relativeLayout[i].findViewById(R.id.tv_bottom_tab);
    			t.setText(strTab[i]);
    			t.setTextColor(getResources().getColor(R.color.textunused));
			}
    	}
    	
    	mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);  
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
    	
        mTabHost.addTab(mTabHost.newTabSpec(Config.KEY_INDEX)  
                .setIndicator(relativeLayout[0]), Frg_Index.class, null); 
        
        mTabHost.addTab(mTabHost.newTabSpec(Config.KEY_CART)  
                .setIndicator(relativeLayout[1]), Frg_Cart.class, null);
        
        mTabHost.addTab(mTabHost.newTabSpec(Config.KEY_USERCENTER)  
                .setIndicator(relativeLayout[2]), Frg_UserCenter.class, null);
        
        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				View v = mTabHost.getTabWidget().getChildAt(iTabIndex);
				((ImageView) v.findViewById(R.id.iv_bottom_tab)).setBackgroundResource(iTab[0][iTabIndex]);
				((TextView) v.findViewById(R.id.tv_bottom_tab)).setTextColor(getResources().getColor(R.color.textunused));
				v = mTabHost.getCurrentTabView();
				iTabIndex = mTabHost.getCurrentTab();
				((ImageView) v.findViewById(R.id.iv_bottom_tab)).setBackgroundResource(iTab[1][iTabIndex]);
				((TextView) v.findViewById(R.id.tv_bottom_tab)).setTextColor(getResources().getColor(R.color.textused));
			}
		});
		
    }
}




























