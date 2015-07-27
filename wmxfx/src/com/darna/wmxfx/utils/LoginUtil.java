package com.darna.wmxfx.utils;

import java.util.Map;

import android.content.Context;
import android.widget.Toast;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.net.NetLocate;

public class LoginUtil {
	
	public void addressSet(final Context mContext){
		Map<String, String> poiMap = Config.getCachePOI(mContext);
		if (poiMap.get(Config.KEY_POSTITLE) == null) {//本地没有地址缓存
			
		}else {//本地有地址缓存，重传服务器，设置服务器的缓存
			new NetLocate(Config.getCachedToken(mContext), poiMap.get(Config.KEY_POSTITLE), 
					poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
						@Override
						public void onSuccess() {
							//Toast.makeText(mContext, "重新定位失败", Toast.LENGTH_SHORT).show();
						}
					}, new NetLocate.FailCallback() {
						@Override
						public void onFail() {
							//System.out.println("上传缓存地址到服务器失败");
						}
					});
		}
	}
	
}
