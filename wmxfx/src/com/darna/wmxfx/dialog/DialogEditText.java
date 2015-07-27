package com.darna.wmxfx.dialog;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.atys.Aty_Order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class DialogEditText extends Activity {
	EditText et_text;
	String text = "";
	String isNeedInvoice = null;
	String invoice = null;
	String flag = null;
	RelativeLayout rl_eidtpop, rl_head;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_edittext);
		init();
		
		flag = getIntent().getStringExtra(Config.KEY_FLAG);
		if(flag.equals("invoice")){
			isNeedInvoice = getIntent().getStringExtra(Config.KEY_NEEDINVOICE);
			if (!isNeedInvoice.equals("0")) {
				invoice = getIntent().getStringExtra(Config.KEY_INVOICE);
				et_text.setText(invoice);
			}else {
				et_text.setHint("请输入发票抬头");
			}
		}else {
			String note = getIntent().getStringExtra(Config.KEY_NOTE);
			if(note.equals("")){
				et_text.setHint("填写备注");
			}else {
				et_text.setText(note);
			}
			
		}
		
	}
	private void init() {
		et_text = (EditText) findViewById(R.id.et_text);
		rl_eidtpop = (RelativeLayout) findViewById(R.id.rl_eidtpop);
		rl_eidtpop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		rl_head = (RelativeLayout) findViewById(R.id.rl_head);
		rl_head.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	public void finish() {
		if (!TextUtils.isEmpty(et_text.getText().toString())) {
			text = et_text.getText().toString();
		}
		Intent intent = new Intent(DialogEditText.this, Aty_Order.class);
		intent.putExtra(Config.KEY_TEXT, text);
		if(flag.equals("invoice")){
			setResult(Config.DIALOG_INVOICE, intent);
		}else {
			setResult(Config.DIALOG_NOTE, intent);
		}
		
		super.finish();
	}
}
