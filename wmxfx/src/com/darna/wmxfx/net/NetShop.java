package com.darna.wmxfx.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.Shop;

public class NetShop {
	public NetShop(final Context mContext, final String token, String search_key, String shop_type_sub, String sort, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)) {
							if (jsonObject.getBoolean(Config.KEY_LOCATE)) {
								if (successCallback != null) {
									List<Shop> shops = new ArrayList<Shop>();
									JSONArray shopArray = jsonObject.getJSONArray(Config.KEY_DATA);
									JSONObject shopObject;
									Shop shop;
									for (int i = 0; i < shopArray.length(); i++) {
										shopObject = shopArray.getJSONObject(i);
										shop = new Shop();
										shop.setShop_id(shopObject.getString(Config.KEY_SHOPID));
										shop.setShop_name(shopObject.getString(Config.KEY_SHOPNAME));
										shop.setShop_thumb(shopObject.getString(Config.KEY_SHOPTHUMB));
										shop.setDistance(shopObject.getString(Config.KEY_DISTANCE));
										shop.setDeli_time(shopObject.getString(Config.KEY_DELITIME));
										shop.setNotice(shopObject.getString(Config.KEY_NOTICE));
										shops.add(shop);
									}
									successCallback.onSuccess(shops);
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
		}, Config.KEY_ACTION, Config.ACTION_SHOPLIST,
		   Config.KEY_TOKEN, token,
		   Config.KEY_SEARCHKEY, search_key,
		   Config.KEY_SHOPTYPESUB, shop_type_sub,
		   Config.KEY_SORT, sort);
	}
	
	public static interface SuccessCallback{
		void onSuccess(List<Shop> shops);
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
}
