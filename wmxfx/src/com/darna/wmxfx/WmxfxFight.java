package com.darna.wmxfx;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.darna.wmxfx.atys.Aty_Location;
import com.darna.wmxfx.bean.POI;
import com.darna.wmxfx.net.NetGetLocation;
import com.darna.wmxfx.net.NetLocate;
import com.darna.wmxfx.utils.BaiduUtil;

public class WmxfxFight extends Activity {
	BaiduUtil baiduUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		baiduUtil = new BaiduUtil(this);
		baiduUtil.start();
		//getLocation();
	}
	
	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		baiduUtil.stop();
		super.onDestroy();
	}
	
	
	/**
	 * 根据token获取服务器中的定位信息
	 * 定位信息存在跳主页面
	 * 不存在 把新的token保存并替换原来的，并调setAddressInSession()方法
	 */
	public void getLocation(){
		new NetGetLocation(WmxfxFight.this, Config.getCachedToken(this), new NetGetLocation.SuccessCallback() {
			@Override
			public void onSuccess(POI poi) {
				startActivity(new Intent(WmxfxFight.this, MainActivity.class));
				finish();
			}
		}, new NetGetLocation.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)) {
					System.out.println("获取地址信息失败！");
					Toast.makeText(WmxfxFight.this, "服务器忙，请稍后再试", Toast.LENGTH_LONG).show();
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
					setAddressInSession();
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession();
				}
				
			}
		});
	}

	/**
	 * 判断本地有没有地址缓存
	 * 没有： 跳到定位页面
	 * 有：上传地址缓存到服务器，回调函数中成功跳主页面，失败输出失败信息
	 */
	private void setAddressInSession() {
		Map<String, String> poiMap = Config.getCachePOI(WmxfxFight.this);
		if (poiMap.get(Config.KEY_POSTITLE) == null) {//本地没有地址缓存
			startActivity(new Intent(WmxfxFight.this, Aty_Location.class));
			finish();
		}else {//本地有地址缓存，重传服务器，设置服务器的缓存
			new NetLocate(Config.getCachedToken(WmxfxFight.this), poiMap.get(Config.KEY_POSTITLE), 
					poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
						@Override
						public void onSuccess() {
							startActivity(new Intent(WmxfxFight.this, MainActivity.class));
							finish();
						}
					}, new NetLocate.FailCallback() {
						@Override
						public void onFail() {
							System.out.println("上传缓存地址到服务器失败");
						}
					});
		}
	}
}
