package com.darna.wmxfx.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.API_CartDish;
import com.darna.wmxfx.bean.API_CartShop;
import com.darna.wmxfx.bean.API_Order;


public class NetHistoryOrder {
	
	public NetHistoryOrder(final Context mContext, final String token, String order_status, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)){
							if (jsonObject.getBoolean(Config.KEY_LOGIN)) {
								if (!jsonObject.isNull(Config.KEY_DATA)) {
									if (successCallback != null) {
										JSONArray unfinishedJsonArray = jsonObject.getJSONArray(Config.KEY_DATA);
										List<API_Order> orders = new ArrayList<API_Order>();
										API_Order order;
										JSONObject unfinishJsonObject;
										for (int i = 0; i < unfinishedJsonArray.length(); i++) {
											order = new API_Order();
											unfinishJsonObject = unfinishedJsonArray.getJSONObject(i);
											order.setOrder_sn(unfinishJsonObject.getString(Config.KEY_SN));
											order.setOrder_amount(unfinishJsonObject.getString(Config.KEY_ORDERAMOUNT));
											order.setAdd_time(unfinishJsonObject.getString(Config.KEY_ADDTIME));
											order.setStatus(unfinishJsonObject.getString(Config.KEY_STA));
											//order.setUser_name(unfinishJsonObject.getString(Config.KEY_USERNAME));
											//order.setRecipient_phone(unfinishJsonObject.getString(Config.KEY_RECIPIENT_PHONE));
											//order.setAddress(unfinishJsonObject.getString(Config.KEY_ADDRESS));
											
											JSONArray shopJsonArray = unfinishJsonObject.getJSONArray(Config.KEY_JUSTSHOP);
											List<API_CartShop> shops = new ArrayList<API_CartShop>();
											API_CartShop shop;
											JSONObject shopJsonObject;
											for (int j = 0; j < shopJsonArray.length(); j++) {
												shop = new API_CartShop();
												shopJsonObject = shopJsonArray.getJSONObject(j);
												shop.setShop_name(shopJsonObject.getString(Config.KEY_SHOPNAME));
												shop.setShop_id(shopJsonObject.getString(Config.KEY_SHOPID));
												
												JSONArray dishJsonArray = shopJsonObject.getJSONArray(Config.KEY_DISH);
												List<API_CartDish> dishes = new ArrayList<API_CartDish>();
												API_CartDish dish;
												JSONObject dishJsonObject;
												for (int k = 0; k < dishJsonArray.length(); k++) {
													dish = new API_CartDish();
													dishJsonObject = dishJsonArray.getJSONObject(k);
													dish.setDish_id(dishJsonObject.getString(Config.KEY_DISH_ID));
													dish.setDish_name(dishJsonObject.getString(Config.KEY_DISH_NAME));
													dish.setNumber(dishJsonObject.getInt(Config.KEY_NUMBER));
													dishes.add(dish);
												}
												shop.setDish_list(dishes);
												shops.add(shop);
											}
											order.setShops(shops);
											orders.add(order);
										}
										successCallback.onSuccess(orders);
									}
								}else {
									if (failCallback != null) {
										failCallback.onFail(Config.RESULT_DATA_NULL);
									}
								}
							}else {
								if (failCallback != null) {
									failCallback.onFail(Config.RESULT_STATUS_UNLOGIN);
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
					if (failCallback != null) {
						failCallback.onFail(Config.RESULT_STATUS_FAIL);
					}
					e.printStackTrace();
				}
			}
		}, new NetConnection.FailCallback() {
			@Override
			public void onFail() {
				if (failCallback != null) {
					failCallback.onFail(Config.RESULT_STATUS_FAIL);
				}
			}
		}, Config.KEY_ACTION, Config.ACTION_HISTORYORDER,
		   Config.KEY_TOKEN, token,
		   Config.KEY_ORDERSTATUS, order_status);
	}
	
	public static interface SuccessCallback{
		void onSuccess(List<API_Order> orders);
	}
	public static interface FailCallback{
		void onFail(String errorCode);
	}
}
