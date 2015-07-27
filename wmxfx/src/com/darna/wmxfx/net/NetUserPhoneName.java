package com.darna.wmxfx.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.UserPhoneName;



public class NetUserPhoneName {

	public NetUserPhoneName(final Context mContext, final String token, final SuccessCallback successCallback, final FailCallback failCallback) {
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
										JSONObject dataObject = jsonObject.getJSONObject(Config.KEY_DATA);
										UserPhoneName userPhoneName = new UserPhoneName();
										userPhoneName.setMobile_phone(dataObject.getString(Config.KEY_MOBILE_PHONE));
										userPhoneName.setAlias(dataObject.getString(Config.KEY_ALIAS));
										successCallback.onSuccess(userPhoneName);
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
		}, Config.KEY_ACTION, Config.ACTION_USERPHONENAME,
		   Config.KEY_TOKEN, token);
	}
	
	public static interface SuccessCallback{
		void onSuccess(UserPhoneName userPhoneName);
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
	
}
