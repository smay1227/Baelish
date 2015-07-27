package com.darna.wmxfx.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.darna.wmxfx.Config;

import android.content.Context;

public class NetLogin {
	
	public NetLogin(final Context mContext, final String token, String phone, String password, final SuccessCallback successCallback,
			final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)) {
							if (jsonObject.getBoolean(Config.KEY_LOCATE)) {
								if (jsonObject.getBoolean(Config.KEY_LOGIN)) {
									if (successCallback != null) {
										successCallback.onSuccess(); 
									}
								}else {
									if (failCallback != null) {
										failCallback.onFail(Config.RESULT_STATUS_UNLOGIN);
									}
								}
							}else {
								if (failCallback != null) {
									failCallback.onFail(Config.RESULT_STATUS_UNLOCATE);
								}
							}
						}else {
							Config.cacheToken(mContext, jsonObject.getString(Config.KEY_TOKEN));
							if (failCallback != null) {
								failCallback.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
							}
						}
					}else {
						if (failCallback != null) {
							failCallback.onFail(Config.RESULT_STATUS_FAIL);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
					if (failCallback != null) {
						failCallback.onFail(Config.RESULT_STATUS_FAIL);
					}
				}
			}
		}, new NetConnection.FailCallback() {
			@Override
			public void onFail() {
				if (failCallback != null) {
					failCallback.onFail(Config.RESULT_STATUS_FAIL);
				}
			}
		}, Config.KEY_ACTION, Config.ACTION_LOGIN,
		   Config.KEY_TOKEN, token,
		   Config.KEY_MOBILE, phone,
		   Config.KEY_PASSWORD, password);
	}
	
	public static interface SuccessCallback{
		void onSuccess();
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
}
