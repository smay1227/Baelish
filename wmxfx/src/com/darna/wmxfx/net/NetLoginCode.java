package com.darna.wmxfx.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.utils.LoginUtil;

import android.content.Context;

public class NetLoginCode {
	public NetLoginCode(final Context mContext, final String token, String mobile, String code, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						JSONObject dataObject = jsonObject.getJSONObject(Config.KEY_DATA);
						String mobile = dataObject.getString(Config.KEY_MOBILE);
						String password = dataObject.getString(Config.KEY_PASSWORD);
						Config.cacheLogin(mContext, mobile, password);
						
						if (!jsonObject.getString(Config.KEY_TOKEN).equals(token)) {
							Config.cacheToken(mContext, jsonObject.getString(Config.KEY_TOKEN));
						}
						if (!jsonObject.getBoolean(Config.KEY_LOCATE)) {
							if (failCallback != null) {
								failCallback.onFail(Config.RESULT_STATUS_UNLOCATE);
							}
						}else {
							if (successCallback != null) {
								successCallback.onSuccess();
							}
						}
					}else {
						if (jsonObject.getString(Config.KEY_CODE).equals("verification_error")) {
							if (failCallback != null) {
								failCallback.onFail(Config.RESULT_STATUS_CODE);
							}
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
		}, Config.KEY_ACTION, Config.ACTION_CODELOGIN,
		   Config.KEY_TOKEN, token,
		   Config.KEY_MOBILE, mobile,
		   Config.KEY_CODE, code);
	}
	
	public static interface SuccessCallback{
		void onSuccess();
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
}
