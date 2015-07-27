package com.darna.wmxfx.atys;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.net.NetUserAddressAdd;
import com.darna.wmxfx.utils.RegularUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Aty_CommonAddressAdd extends Activity{
	EditText et_contacter, et_contactephone, et_contactaddrinfo;
	Button btn_save;
	TextView et_contactaddr;
	ImageView iv_rleft;
	RegularUtil regularUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_commonaddressadd);
		
		regularUtil = new RegularUtil();
		
		et_contacter = (EditText) findViewById(R.id.et_contacter);
		et_contactephone = (EditText) findViewById(R.id.et_contactephone);
		et_contactaddr = (TextView) findViewById(R.id.et_contactaddr);
		et_contactaddrinfo = (EditText) findViewById(R.id.et_contactaddrinfo);
		btn_save = (Button) findViewById(R.id.btn_save);
		iv_rleft = (ImageView) findViewById(R.id.iv_rleft);
		
		et_contactaddr.setText(Config.getCachePOI(this).get(Config.KEY_POSTITLE));
		
		btn_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSaveOnClick();
			}
		});
		
		iv_rleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
	
	private void setSaveOnClick() {
		String phone = et_contactephone.getText().toString();
		String consignee = et_contacter.getText().toString();
		String detailed_address = et_contactaddrinfo.getText().toString();
		
		if (regularUtil.checkPhone(phone)) {
			new NetUserAddressAdd(this, Config.getCachedToken(this), detailed_address, consignee, phone, new NetUserAddressAdd.SuccessCallback() {
				@Override
				public void onSuccess(String success) {
					finish();
				}
			}, new NetUserAddressAdd.FailCallback() {
				@Override
				public void onFail(String errorCode) {
					Toast.makeText(Aty_CommonAddressAdd.this, "保存送餐地址失败", Toast.LENGTH_SHORT).show();
				}
			});
		}else {
			Toast.makeText(Aty_CommonAddressAdd.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
		}
	}
}
