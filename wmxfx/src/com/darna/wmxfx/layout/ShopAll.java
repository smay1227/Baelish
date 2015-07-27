package com.darna.wmxfx.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.dialog.ShopTasteDialog;

/**
 * 
 * @author xiashuai
 * 使用下拉刷新
 * 弃用
 */
public class ShopAll extends FrameLayout {
	RelativeLayout sequenceLayout, tasteLayout;
	private int shoptasteCode = 0;
	public ShopAll(Context context) {
		this(context, null);
	}
	
	public ShopAll(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ShopAll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		InitUI(context);
	}
	
	
	private void InitUI(Context context){
		LayoutInflater.from(context).inflate(R.layout.shopall, this, true);
		sequenceLayout = (RelativeLayout) findViewById(R.id.rl_sequence);
		tasteLayout = (RelativeLayout) findViewById(R.id.rl_alltaste);
		
		sequenceLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		tasteLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//v.getContext().startActivity(new Intent(v.getContext(), ShopTasteDialog.class));
				//((Activity)v.getContext()).startActivityForResult(intent, requestCode, options);
				Intent intent = new Intent(v.getContext(), ShopTasteDialog.class);
				intent.putExtra(Config.KEY_TASTECODE, shoptasteCode);
				((Activity)v.getContext()).startActivityForResult(intent, 1);
				
			}
		});
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
