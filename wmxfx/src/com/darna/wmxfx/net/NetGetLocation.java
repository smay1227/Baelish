package com.darna.wmxfx.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.WmxfxFight;
import com.darna.wmxfx.bean.POI;

public class NetGetLocation{
	public NetGetLocation(final Context mContext, final String token, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)) {
							if (jsonObject.getBoolean(Config.KEY_LOCATE)) {
								if (successCallback != null) {
									POI poi = new POI();
									JSONObject dataJsonObject = jsonObject.getJSONObject(Config.KEY_DATA);
									poi.setPosTitle(dataJsonObject.getString(Config.KEY_POSTITLE));
									poi.setPosX(dataJsonObject.getString(Config.KEY_POSX));
									poi.setPosY(dataJsonObject.getString(Config.KEY_POSY));
									poi.setPosAddress(dataJsonObject.getString(Config.KEY_POSADDRESS));
									successCallback.onSuccess(poi);
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
		}, Config.KEY_ACTION, Config.ACTION_GETLOCATION,
		   Config.KEY_TOKEN, token);
	}
	
	public static interface SuccessCallback{
		void onSuccess(POI poi);
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
	
}
