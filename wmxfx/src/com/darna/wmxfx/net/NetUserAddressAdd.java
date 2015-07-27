package com.darna.wmxfx.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;


public class NetUserAddressAdd {
	public NetUserAddressAdd(final Context mContext, final String token, 
							 String detailed_address, String consignee, String recipient_phone,
							 final SuccessCallback successCallback, final FailCallback failCallback) {
		
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)){
							if (successCallback != null) {
								successCallback.onSuccess("访问成功");
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
		}, Config.KEY_ACTION, Config.ACTION_ADDUSERADDRESS,
		   Config.KEY_TOKEN, token,
		   Config.KEY_DETAILEDADDRESS, detailed_address,
		   Config.KEY_CONSIGNEE, consignee,
		   Config.KEY_RECIPIENT_PHONE, recipient_phone);
		
	}
	
	public static interface SuccessCallback{
		void onSuccess(String success);
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
	
}
























