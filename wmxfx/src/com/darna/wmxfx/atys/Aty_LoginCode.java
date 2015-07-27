package com.darna.wmxfx.atys;

import java.util.List;
import java.util.Map;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.bean.UserAddress;
import com.darna.wmxfx.net.NetLocate;
import com.darna.wmxfx.net.NetLoginCode;
import com.darna.wmxfx.net.NetUserAddress;
import com.darna.wmxfx.net.NetVerifySMS;
import com.darna.wmxfx.utils.RegularUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Aty_LoginCode extends Activity{
	EditText tv_phone, tv_mail;
	Button btn_getcode, btn_login;
	RegularUtil regularUtil;
	MyCount mc;
	String flag = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_logincode);
		regularUtil = new RegularUtil();
		tv_phone = (EditText) findViewById(R.id.tv_phone);
		tv_mail = (EditText) findViewById(R.id.tv_mail);
		btn_getcode = (Button) findViewById(R.id.btn_getcode);
		btn_login = (Button) findViewById(R.id.btn_login);
		
		flag = getIntent().getStringExtra(Config.KEY_FLAG);
		
		btn_getcode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String phone = tv_phone.getText().toString();
				if (regularUtil.checkPhone(phone)) {
					getCode(phone);
				}else {
					Toast.makeText(Aty_LoginCode.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String phone = tv_phone.getText().toString();
				String code = tv_mail.getText().toString();
				if (!regularUtil.checkPhone(phone)) {
					Toast.makeText(Aty_LoginCode.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
				}else if (!regularUtil.checkCode(code)) {
					Toast.makeText(Aty_LoginCode.this, "请输入正确的6位数验证码", Toast.LENGTH_SHORT).show();
				}else {
					login(phone, code);
					btn_login.setClickable(false);
				}
			}
		});
	}
	
	public void login(String mobile, String code){
		new NetLoginCode(this, Config.getCachedToken(this), mobile, code, new NetLoginCode.SuccessCallback() {
			@Override
			public void onSuccess() {
				if (!flag.equals("USERCENTER")) {
					getAddressData();
				}
				finish();
			}
		}, new NetLoginCode.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					btn_login.setClickable(true);
					System.out.println("验证码登陆失败");
					
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					btn_login.setClickable(true);
					addressSet();
				}else if (errorCode.equals(Config.RESULT_STATUS_CODE)) {
					btn_login.setClickable(true);
					Toast.makeText(Aty_LoginCode.this, "验证码错误，请稍后再试", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void getAddressData() {
		new NetUserAddress(this, Config.getCachedToken(this), new NetUserAddress.SuccessCallback() {
			@Override
			public void onSuccess(List<UserAddress> addresses) {
				if (addresses.size() > 0) {
					startActivity(new Intent(Aty_LoginCode.this, Aty_Order.class));
				}else {
					startActivity(new Intent(Aty_LoginCode.this, Aty_CommonAddressAdd.class));
				}
			}
		}, new NetUserAddress.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)) {
					System.out.println("获取数据失败");
				}else if (errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)) {
					//进行登录和定位操作，然后再调用这个方法
					
				}
			}
		});
	}
	
	public void addressSet(){
		Map<String, String> poiMap = Config.getCachePOI(this);
		if (poiMap.get(Config.KEY_POSTITLE) == null) {//本地没有地址缓存
			
		}else {//本地有地址缓存，重传服务器，设置服务器的缓存
			new NetLocate(Config.getCachedToken(this), poiMap.get(Config.KEY_POSTITLE), 
					poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
						@Override
						public void onSuccess() {
							Toast.makeText(Aty_LoginCode.this, "先传地址后登陆成功", Toast.LENGTH_SHORT).show();
						}
					}, new NetLocate.FailCallback() {
						@Override
						public void onFail() {
							Toast.makeText(Aty_LoginCode.this, "先传地址后登陆失败", Toast.LENGTH_SHORT).show();
						}
					});
		}
	}
	
	public void getCode(String phone){
		new NetVerifySMS(Config.getCachedToken(this), phone, new NetVerifySMS.SuccessCallback() {
			@Override
			public void onSuccess() {
				if(mc == null){
					mc = new MyCount(60000, 1000);
				}
				mc.start();
			}
		}, new NetVerifySMS.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				Toast.makeText(Aty_LoginCode.this, "短信没有发送成功", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	class MyCount extends CountDownTimer{

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			btn_getcode.setEnabled(false);
			btn_getcode.setText(millisUntilFinished / 1000 + "秒后重新获取");
		}

		@Override
		public void onFinish() {
			btn_getcode.setEnabled(true);
			btn_getcode.setText("获取验证码");
		}
		
	}
	
}
