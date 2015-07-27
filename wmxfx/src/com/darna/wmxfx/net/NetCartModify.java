package com.darna.wmxfx.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;



public class NetCartModify {
	public NetCartModify(final Context mContext, String shopId, String dishId, int num, String attr_code, final String token, 
			             final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)) {
							if (jsonObject.getBoolean(Config.KEY_LOCATE)) {
								if (successCallback != null) {
									successCallback.onSuccess("访问成功");
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
		}, Config.KEY_ACTION, Config.ACTION_ADDSUBTRACTCART,
		   Config.KEY_SHOPID, shopId,
		   Config.KEY_DISH_ID, dishId,
		   Config.KEY_NUM, num+"",
		   Config.KEY_ATTRCODE, attr_code,
		   Config.KEY_TOKEN, token);
	}
	
	public static interface SuccessCallback{
		void onSuccess(String success);
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
	
}
























