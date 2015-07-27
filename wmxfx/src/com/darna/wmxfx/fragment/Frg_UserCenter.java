package com.darna.wmxfx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.atys.Aty_LoginCode;
import com.darna.wmxfx.atys.Aty_OrderOfMine;
import com.darna.wmxfx.atys.Aty_logout;
import com.darna.wmxfx.bean.UserPhoneName;
import com.darna.wmxfx.net.NetUserPhoneName;

public class Frg_UserCenter extends Fragment{
	TextView tv_userName, tv_userPhone;
	RelativeLayout rl_intergral, rl_useraddress, rl_userorder, rl_uservoucher;
	Button btn_logreg;
	View view;
	Boolean isLogin;
	ImageView iv_setting;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.frg_usercenter, container, false);
		init();
		getData();
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getData();
	}
	
	private void getData() {
		new NetUserPhoneName(getActivity(), Config.getCachedToken(getActivity()), new NetUserPhoneName.SuccessCallback() {
			@Override
			public void onSuccess(UserPhoneName userPhoneName) {
				//显示已登陆的页面
				isLogin = true;
				
				btn_logreg.setVisibility(View.GONE);
				tv_userName.setVisibility(View.VISIBLE);
				tv_userPhone.setVisibility(View.VISIBLE);
				
				if (userPhoneName.getAlias().equals("null") || userPhoneName.getAlias().equals("")) {
					tv_userName.setText("外卖小飞侠用户");
				}else {
					tv_userName.setText(userPhoneName.getAlias());
				}
				tv_userPhone.setText(userPhoneName.getMobile_phone());
				
			}
		}, new NetUserPhoneName.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				isLogin = false;
				
				btn_logreg.setVisibility(View.VISIBLE);
				tv_userName.setVisibility(View.GONE);
				tv_userPhone.setVisibility(View.GONE);
				
				logOnclick();
				
				if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					//去上传定位
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOGIN)) {
					//显示没有登陆的页面
					
				}else {
					Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	protected void logOnclick() {
		btn_logreg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});
	}
	
	public void login(){
		Intent intent = new Intent(getActivity(), Aty_LoginCode.class);
		intent.putExtra(Config.KEY_FLAG, "USERCENTER");
		startActivity(intent);
	}

	private void init() {
		tv_userName = (TextView) view.findViewById(R.id.tv_userName);
		tv_userPhone = (TextView) view.findViewById(R.id.tv_userPhone);
		rl_intergral = (RelativeLayout) view.findViewById(R.id.rl_intergral);
		rl_useraddress = (RelativeLayout) view.findViewById(R.id.rl_useraddress);
		rl_userorder = (RelativeLayout) view.findViewById(R.id.rl_userorder);
		rl_uservoucher = (RelativeLayout) view.findViewById(R.id.rl_uservoucher);
		btn_logreg = (Button) view.findViewById(R.id.btn_logreg);
		iv_setting = (ImageView) view.findViewById(R.id.iv_setting);
		
		iv_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), Aty_logout.class));
			}
		});
		
		rl_intergral.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isLogin) {
					login();
				}else {
					Toast.makeText(getActivity(), "intergal", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		rl_useraddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isLogin) {
					login();
				}else {
					Toast.makeText(getActivity(), "useraddress", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		rl_userorder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isLogin) {
					login();
				}else {
					Intent intent = new Intent(getActivity(), Aty_OrderOfMine.class);
					startActivity(intent);
				}
			}
		});
		
		rl_uservoucher.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isLogin) {
					login();
				}else {
					Toast.makeText(getActivity(), "uservoucher", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
}   
