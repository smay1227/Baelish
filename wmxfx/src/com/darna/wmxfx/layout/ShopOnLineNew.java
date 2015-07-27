package com.darna.wmxfx.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.darna.wmxfx.R;

public class ShopOnLineNew extends FrameLayout {
	TextView tvmore;
	public ShopOnLineNew(Context context) {
		this(context, null);
	}
	
	public ShopOnLineNew(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ShopOnLineNew(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initUI(context);
	}
	
	private void initUI(Context context){
		LayoutInflater.from(context).inflate(R.layout.shoponlinenew, this, true);
		
		tvmore = (TextView) findViewById(R.id.tv_more);
		tvmore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("点到我了");
			}
		});
		
	}


}


















