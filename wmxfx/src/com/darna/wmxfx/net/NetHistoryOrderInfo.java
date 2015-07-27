package com.darna.wmxfx.net;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.API_CartDish;
import com.darna.wmxfx.bean.API_CartShop;

public class NetHistoryOrderInfo {
	
	public NetHistoryOrderInfo(final Context mContext, final String token, String order_sn, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)){
							JSONArray shopJsonArray = jsonObject.getJSONArray(Config.KEY_DATA);
							List<API_CartShop> shops = new ArrayList<API_CartShop>();
							API_CartShop shop;
							JSONObject shopJsonObject;
							for (int j = 0; j < shopJsonArray.length(); j++) {
								shop = new API_CartShop();
								shopJsonObject = shopJsonArray.getJSONObject(j);
								shop.setShop_name(shopJsonObject.getString(Config.KEY_SHOPNAME));
								shop.setShop_id(shopJsonObject.getString(Config.KEY_SHOPID));
								shop.setShop_real_price(new BigDecimal(shopJsonObject.getDouble(Config.KEY_CUSTOMREALPRICE)).setScale(1, BigDecimal.ROUND_HALF_UP));
								shop.setConsignee(shopJsonObject.getString(Config.KEY_CONSIGNEE));
								shop.setPay_id(shopJsonObject.getString(Config.KEY_PAYID));
								shop.setRecipient_phone(shopJsonObject.getString(Config.KEY_RECIPIENT_PHONE));
								shop.setAddress(shopJsonObject.getString(Config.KEY_ADDRESS));
								shop.setOrder_sn(shopJsonObject.getString(Config.KEY_SN));
								shop.setShipping_time(shopJsonObject.getString(Config.KEY_SHIPPINGTIME));
								shop.setOrderamount(shopJsonObject.getInt(Config.KEY_ORDERAMOUNT));
								shop.setOrder_status(shopJsonObject.getString(Config.KEY_ORDERSTATUS));
								
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
									dish.setDish_price(new BigDecimal(dishJsonObject.getDouble(Config.KEY_DISH_PRICE)).setScale(1, BigDecimal.ROUND_HALF_UP));
									dishes.add(dish);
								}
								shop.setDish_list(dishes);
								shops.add(shop);
								successCallback.onSuccess(shops);
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
		}, Config.KEY_ACTION, Config.ACTION_ORDERINFO,
		   Config.KEY_TOKEN, token,
		   Config.KEY_ORDERSN, order_sn);
	}
	
	public static interface SuccessCallback{
		void onSuccess(List<API_CartShop> orders);
	}
	public static interface FailCallback{
		void onFail(String errorCode);
	}
}
