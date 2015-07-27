package com.darna.wmxfx.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.fragment.Frg_Index;

public class ShopSequenceDialog extends Activity{
	
	LinearLayout ll_allsequence, ll_lowdelivercost, ll_highgrade, ll_highsale;
	TextView tv_allsequence, tv_lowdelivercost, tv_highgrade, tv_highsale;
	ImageView iv_allsequence, iv_lowdelivercost, iv_highgrade, iv_highsale;
	private String sequencecode = Config.KEY_ALLSEQUENCE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_shopsequence);
		sequencecode = getIntent().getStringExtra(Config.KEY_SEQUENCRCODE);
		init();
	}
	
	private void initCheck(){
		if(sequencecode.equals(Config.KEY_ALLSEQUENCE)){
			tv_allsequence.setTextColor(getResources().getColor(R.color.used));
			iv_allsequence.setImageResource(R.drawable.allsequence);
		}else if (sequencecode.equals(Config.KEY_LOWDELIVER)) {
			tv_lowdelivercost.setTextColor(getResources().getColor(R.color.used));
			iv_lowdelivercost.setImageResource(R.drawable.lowdelivercost);
		}else if (sequencecode.equals(Config.KEY_HIGHGRADE)) {
			tv_highgrade.setTextColor(getResources().getColor(R.color.used));
			iv_highgrade.setImageResource(R.drawable.highgrade);
		}else if (sequencecode.equals(Config.KEY_HIGHSALE)) {
			tv_highsale.setTextColor(getResources().getColor(R.color.used));
			iv_highsale.setImageResource(R.drawable.highsale);
		}
	}
	
	private void init() {
		findView();
		setAlltoUnsed();
		initCheck();
		ll_allsequence.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnsed();
				tv_allsequence.setTextColor(getResources().getColor(R.color.used));
				iv_allsequence.setImageResource(R.drawable.allsequence);
				Intent intent = new Intent(ShopSequenceDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_SEQUENCRCODE, Config.KEY_ALLSEQUENCE);
				setResult(1, intent);
				finish();
			}
		});
		
		ll_lowdelivercost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnsed();
				tv_lowdelivercost.setTextColor(getResources().getColor(R.color.used));
				iv_lowdelivercost.setImageResource(R.drawable.lowdelivercost);
				Intent intent = new Intent(ShopSequenceDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_SEQUENCRCODE, Config.KEY_LOWDELIVER);
				setResult(1, intent);
				finish();
			}
		});
		
		ll_highgrade.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnsed();
				tv_highgrade.setTextColor(getResources().getColor(R.color.used));
				iv_highgrade.setImageResource(R.drawable.highgrade);
				Intent intent = new Intent(ShopSequenceDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_SEQUENCRCODE, Config.KEY_HIGHGRADE);
				setResult(1, intent);
				finish();
			}
		});
		
		ll_highsale.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnsed();
				tv_highsale.setTextColor(getResources().getColor(R.color.used));
				iv_highsale.setImageResource(R.drawable.highsale);
				Intent intent = new Intent(ShopSequenceDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_SEQUENCRCODE, Config.KEY_HIGHSALE);
				setResult(1, intent);
				finish();
			}
		});
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}
	
	private void setAlltoUnsed() {
		tv_allsequence.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_lowdelivercost.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_highgrade.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_highsale.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		
		iv_allsequence.setImageResource(R.drawable.unallsequence);
		iv_lowdelivercost.setImageResource(R.drawable.unlowdelivercost);
		iv_highgrade.setImageResource(R.drawable.unhighgrade);
		iv_highsale.setImageResource(R.drawable.unhighsale);
	}
	
	private void findView() {
		ll_allsequence = (LinearLayout) findViewById(R.id.ll_allsequence);
		tv_allsequence = (TextView) findViewById(R.id.tv_allsequence);
		iv_allsequence = (ImageView) findViewById(R.id.iv_allsequence);
		
		ll_lowdelivercost = (LinearLayout) findViewById(R.id.ll_lowdelivercost);
		tv_lowdelivercost = (TextView) findViewById(R.id.tv_lowdelivercost);
		iv_lowdelivercost = (ImageView) findViewById(R.id.iv_lowdelivercost);
		
		ll_highgrade = (LinearLayout) findViewById(R.id.ll_highgrade);
		tv_highgrade = (TextView) findViewById(R.id.tv_highgrade);
		iv_highgrade = (ImageView) findViewById(R.id.iv_highgrade);
		
		ll_highsale = (LinearLayout) findViewById(R.id.ll_highsale);
		tv_highsale = (TextView) findViewById(R.id.tv_highsale);
		iv_highsale = (ImageView) findViewById(R.id.iv_highsale);
	}
	
}
