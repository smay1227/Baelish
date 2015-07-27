package com.darna.wmxfx.net;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.Dish;
import com.darna.wmxfx.bean.Dishlist;


public class NetDish {
	public NetDish(final Context mContext, final String token, String shopId, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)){
							if (jsonObject.getBoolean(Config.KEY_LOCATE)) {
								if (successCallback != null) {
									List<Dish> dishs = new ArrayList<Dish>();
									JSONArray jsonArray = jsonObject.getJSONArray(Config.KEY_DATA);
									JSONObject dishlistJsonObject;
									JSONArray dishlistJsonArray;
									List<Dishlist> dishlists;
									JSONObject dishJsonObject;
									for (int i = 0; i < jsonArray.length(); i++) {
										dishlistJsonObject = jsonArray.getJSONObject(i);
										dishlistJsonArray = dishlistJsonObject.getJSONArray(Config.KEY_DISH_LIST);
										dishlists = new ArrayList<Dishlist>();
										for (int j = 0; j < dishlistJsonArray.length(); j++) {
											dishJsonObject = dishlistJsonArray.getJSONObject(j);
											dishlists.add(new Dishlist(dishJsonObject.getString(Config.KEY_SHOPID),
																	   dishJsonObject.getString(Config.KEY_DISH_ID),
																	   dishJsonObject.getString(Config.KEY_DISH_NAME),
																	   new BigDecimal(dishJsonObject.getDouble(Config.KEY_PRICE_OLD)).setScale(1, BigDecimal.ROUND_HALF_UP),
																	   new BigDecimal(dishJsonObject.getDouble(Config.KEY_PRICE_NEW)).setScale(1, BigDecimal.ROUND_HALF_UP),
																	  // new BigDecimal(dishJsonObject.getDouble(Config.KEY_PACK_PRICE)).setScale(1, BigDecimal.ROUND_HALF_UP),
																	   new BigDecimal(0),
																	   dishJsonObject.getString(Config.KEY_DISH_THUMB),
																	   dishJsonObject.getBoolean(Config.KEY_HAS_ACTIVITY),
																	   dishJsonObject.getBoolean(Config.KEY_DETAIL_POPUP),
																	   dishJsonObject.getBoolean(Config.KEY_IN_SHORT_SUPPLY)
																	   ));
										}
										dishs.add(new Dish(dishlistJsonObject.getString(Config.KEY_TYPE), dishlists));
									}
									successCallback.onSuccess(dishs);
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
		}, Config.KEY_ACTION, Config.ACTION_DISH,
		   Config.KEY_TOKEN, token,
		   Config.KEY_SHOPID, shopId);
	}
	
	public static interface SuccessCallback{
		void onSuccess(List<Dish> dishes);
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
	
}
