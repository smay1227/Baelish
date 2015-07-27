package com.darna.wmxfx.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.ShopInfo;

public class NetShopInfo {
	public NetShopInfo(final Context mContext,final String token, String shopId, final SuccessCallback successCallback, final FailCallback failCallback){
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)) {
							if (jsonObject.getBoolean(Config.KEY_LOCATE)) {
								if (successCallback != null) {
									ShopInfo shopInfo = new ShopInfo();
									JSONObject dataObject = jsonObject.getJSONObject(Config.KEY_DATA);
									shopInfo.setShop_id(dataObject.getString(Config.KEY_SHOPID));
									shopInfo.setThumb(dataObject.getString(Config.KEY_THUMB));
									shopInfo.setShop_name(dataObject.getString(Config.KEY_SHOPNAME));
									shopInfo.setAddress(dataObject.getString(Config.KEY_ADDRESS));
									shopInfo.setNotice(dataObject.getString(Config.KEY_NOTICE));
									shopInfo.setAverage_cost(dataObject.getString(Config.KEY_AVEREGECOST));
									shopInfo.setBusinesshour(dataObject.getString(Config.KEY_BUSINESSHOUR));
									shopInfo.setActivitydesc(dataObject.getString(Config.KEY_ACTIVITYDESC));
									shopInfo.setDistance(dataObject.getString(Config.KEY_DISTANCE));
									shopInfo.setDeliverycost(dataObject.getString(Config.KEY_DELIVERYCOST));
									shopInfo.setAverage_point((float)dataObject.getDouble(Config.KEY_AVERAGE_POINT));
									
									successCallback.onSuccess(shopInfo);
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
		}, Config.KEY_ACTION, Config.ACTION_SHOPINFO,
		   Config.KEY_TOKEN, token,
		   Config.KEY_SHOPID, shopId);
	}
	
	public static interface SuccessCallback{
		void onSuccess(ShopInfo shopInfo);
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
	
}
