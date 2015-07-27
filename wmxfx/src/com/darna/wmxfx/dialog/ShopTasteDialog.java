package com.darna.wmxfx.dialog;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.fragment.Frg_Index;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShopTasteDialog extends Activity{
	LinearLayout ll_all, ll_fastfood, ll_chinafood, ll_westfood, ll_tokyofood, ll_foreignfood, ll_drink, ll_snackfood, ll_cake, ll_donut;
	TextView tv_all, tv_fastfood, tv_chinafood, tv_westfood, tv_tokyofood, tv_foreignfood, tv_drink, tv_snackfood, tv_cake, tv_donut;
	ImageView iv_all, iv_fastfood, iv_chinafood, iv_westfood, iv_tokyofood, iv_foreignfood, iv_drink, iv_snackfood, iv_cake, iv_donut;
	private String tastecode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_shoptaste);
		tastecode = getIntent().getStringExtra(Config.KEY_TASTECODE);
		init();
	}
	
	private void initChecked(){
		if (tastecode.equals(Config.KEY_ALLTASTE)) {
			tv_all.setTextColor(getResources().getColor(R.color.used));
			iv_all.setImageResource(R.drawable.alltaste);
		}else if (tastecode.equals(Config.KEY_FASTFOOD)) {
			tv_fastfood.setTextColor(getResources().getColor(R.color.used));
			iv_fastfood.setImageResource(R.drawable.fastfood);
		}else if (tastecode.equals(Config.KEY_CHINAFOOD)) {
			tv_chinafood.setTextColor(getResources().getColor(R.color.used));
			iv_chinafood.setImageResource(R.drawable.chinafood);
		}else if (tastecode.equals(Config.KEY_WESTFOOD)) {
			tv_westfood.setTextColor(getResources().getColor(R.color.used));
			iv_westfood.setImageResource(R.drawable.westfood);
		}else if (tastecode.equals(Config.KEY_TOKYOFOOD)) {
			tv_tokyofood.setTextColor(getResources().getColor(R.color.used));
			iv_tokyofood.setImageResource(R.drawable.tokyofood);
		}else if (tastecode.equals(Config.KEY_FOREIGNFOOD)) {
			tv_foreignfood.setTextColor(getResources().getColor(R.color.used));
			iv_foreignfood.setImageResource(R.drawable.foreignfood);
		}else if (tastecode.equals(Config.KEY_DRINK)) {
			tv_drink.setTextColor(getResources().getColor(R.color.used));
			iv_drink.setImageResource(R.drawable.drink);
		}else if (tastecode.equals(Config.KEY_SNACKFOOD)) {
			tv_snackfood.setTextColor(getResources().getColor(R.color.used));
			iv_snackfood.setImageResource(R.drawable.snackfood);
		}else if (tastecode.equals(Config.KEY_CAKE)) {
			tv_cake.setTextColor(getResources().getColor(R.color.used));
			iv_cake.setImageResource(R.drawable.cake);
		}else if (tastecode.equals(Config.KEY_DONUT)) {
			tv_donut.setTextColor(getResources().getColor(R.color.used));
			iv_donut.setImageResource(R.drawable.donut);
		}
	}
	
	private void init(){
		findView();
		setAlltoUnused();
		initChecked();
		
		ll_all.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnused();
				tv_all.setTextColor(getResources().getColor(R.color.used));
				iv_all.setImageResource(R.drawable.alltaste);
				Intent intent = new Intent(ShopTasteDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_TASTECODE, Config.KEY_ALLTASTE);
				setResult(0, intent);
				finish();
			}
		});
		
		ll_fastfood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnused();
				tv_fastfood.setTextColor(getResources().getColor(R.color.used));
				iv_fastfood.setImageResource(R.drawable.fastfood);
				Intent intent = new Intent(ShopTasteDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_TASTECODE, Config.KEY_FASTFOOD);
				setResult(0, intent);
				finish();
			}
		});
		
		ll_chinafood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnused();
				tv_chinafood.setTextColor(getResources().getColor(R.color.used));
				iv_chinafood.setImageResource(R.drawable.chinafood);
				Intent intent = new Intent(ShopTasteDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_TASTECODE, Config.KEY_CHINAFOOD);
				setResult(0, intent);
				finish();
			}
		});
		
		ll_westfood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnused();
				tv_westfood.setTextColor(getResources().getColor(R.color.used));
				iv_westfood.setImageResource(R.drawable.westfood);
				Intent intent = new Intent(ShopTasteDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_TASTECODE, Config.KEY_WESTFOOD);
				setResult(0, intent);
				finish();
			}
		});
		
		ll_tokyofood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnused();
				tv_tokyofood.setTextColor(getResources().getColor(R.color.used));
				iv_tokyofood.setImageResource(R.drawable.tokyofood);
				Intent intent = new Intent(ShopTasteDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_TASTECODE, Config.KEY_TOKYOFOOD);
				setResult(0, intent);
				finish();
			}
		});
		
		ll_foreignfood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnused();
				tv_foreignfood.setTextColor(getResources().getColor(R.color.used));
				iv_foreignfood.setImageResource(R.drawable.foreignfood);
				Intent intent = new Intent(ShopTasteDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_TASTECODE, Config.KEY_FOREIGNFOOD);
				setResult(0, intent);
				finish();
			}
		});
		
		ll_drink.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnused();
				tv_drink.setTextColor(getResources().getColor(R.color.used));
				iv_drink.setImageResource(R.drawable.drink);
				Intent intent = new Intent(ShopTasteDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_TASTECODE, Config.KEY_DRINK);
				setResult(0, intent);
				finish();
			}
		});
		
		ll_snackfood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnused();
				tv_snackfood.setTextColor(getResources().getColor(R.color.used));
				iv_snackfood.setImageResource(R.drawable.snackfood);
				Intent intent = new Intent(ShopTasteDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_TASTECODE, Config.KEY_SNACKFOOD);
				setResult(0, intent);
				finish();
			}
		});
		
		ll_cake.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnused();
				tv_cake.setTextColor(getResources().getColor(R.color.used));
				iv_cake.setImageResource(R.drawable.cake);
				Intent intent = new Intent(ShopTasteDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_TASTECODE, Config.KEY_CAKE);
				setResult(0, intent);
				finish();
			}
		});
		
		ll_donut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setAlltoUnused();
				tv_donut.setTextColor(getResources().getColor(R.color.used));
				iv_donut.setImageResource(R.drawable.donut);
				Intent intent = new Intent(ShopTasteDialog.this, Frg_Index.class);
				intent.putExtra(Config.KEY_TASTECODE, Config.KEY_DONUT);
				setResult(0, intent);
				finish();
			}
		});
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Intent intent = new Intent(ShopTasteDialog.this, Frg_Index.class);
		//intent.putExtra(Config.KEY_TASTECODE, tastecode);
		//ShopTasteDialog.this.setResult(0, intent);//待改
		finish();
		return true;
	}
	
	private void setAlltoUnused(){
		tv_all.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_fastfood.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_chinafood.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_westfood.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_tokyofood.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_foreignfood.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_drink.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_snackfood.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_cake.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		tv_donut.setTextColor(getResources().getColor(R.color.bottom_backcolor));
		
		iv_all.setImageResource(R.drawable.unalltaste);
		iv_fastfood.setImageResource(R.drawable.unfastfood);
		iv_chinafood.setImageResource(R.drawable.unchinafood);
		iv_westfood.setImageResource(R.drawable.unwestfood);
		iv_tokyofood.setImageResource(R.drawable.untokyofood);
		iv_foreignfood.setImageResource(R.drawable.unforeignfood);
		iv_drink.setImageResource(R.drawable.undrink);
		iv_snackfood.setImageResource(R.drawable.unsnackfood);
		iv_cake.setImageResource(R.drawable.uncake);
		iv_donut.setImageResource(R.drawable.undonut);
	}
	
	private void findView(){
		ll_all = (LinearLayout) findViewById(R.id.ll_all);
		tv_all = (TextView) findViewById(R.id.tv_all);
		iv_all = (ImageView) findViewById(R.id.iv_all);
		
		ll_fastfood = (LinearLayout) findViewById(R.id.ll_fastfood);
		tv_fastfood = (TextView) findViewById(R.id.tv_fastfood);
		iv_fastfood = (ImageView) findViewById(R.id.iv_fastfood);
		
		ll_chinafood = (LinearLayout) findViewById(R.id.ll_chinafood);
		tv_chinafood = (TextView) findViewById(R.id.tv_chinafood);
		iv_chinafood = (ImageView) findViewById(R.id.iv_chinafood);
		
		ll_westfood = (LinearLayout) findViewById(R.id.ll_westfood);
		tv_westfood = (TextView) findViewById(R.id.tv_westfood);
		iv_westfood = (ImageView) findViewById(R.id.iv_westfood);
		
		ll_tokyofood = (LinearLayout) findViewById(R.id.ll_tokyofood);
		tv_tokyofood = (TextView) findViewById(R.id.tv_tokyofood);
		iv_tokyofood = (ImageView) findViewById(R.id.iv_tokyofood);
		
		ll_foreignfood = (LinearLayout) findViewById(R.id.ll_foreignfood);
		tv_foreignfood = (TextView) findViewById(R.id.tv_foreignfood);
		iv_foreignfood = (ImageView) findViewById(R.id.iv_foreignfood);
		
		ll_drink = (LinearLayout) findViewById(R.id.ll_drink);
		tv_drink = (TextView) findViewById(R.id.tv_drink);
		iv_drink = (ImageView) findViewById(R.id.iv_drink);
		
		ll_snackfood = (LinearLayout) findViewById(R.id.ll_snackfood);
		tv_snackfood = (TextView) findViewById(R.id.tv_snackfood);
		iv_snackfood = (ImageView) findViewById(R.id.iv_snackfood);
		
		ll_cake = (LinearLayout) findViewById(R.id.ll_cake);
		tv_cake = (TextView) findViewById(R.id.tv_cake);
		iv_cake = (ImageView) findViewById(R.id.iv_cake);
		
		ll_donut = (LinearLayout) findViewById(R.id.ll_donut);
		tv_donut = (TextView) findViewById(R.id.tv_donut);
		iv_donut = (ImageView) findViewById(R.id.iv_donut);
	}
	
	
}
