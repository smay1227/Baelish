package com.darna.wmxfx.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.darna.wmxfx.Config;

public class NetVerifySMS {
	public NetVerifySMS(String token, String phone, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (successCallback != null) {
							successCallback.onSuccess();
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
		}, Config.KEY_ACTION, Config.ACTION_VERIFYSMS,
		   Config.KEY_TOKEN, token,
		   Config.KEY_PHONENUM, phone);
	}
	
	public static interface SuccessCallback{
		void onSuccess();
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
}
