package com.darna.wmxfx.net;


import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.DishDetail;


public class NetDishDetail {
	public NetDishDetail(final Context mContext, final String token, String shopId, String dishId, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)){
							if (jsonObject.getBoolean(Config.KEY_LOCATE)) {
								if (successCallback != null) {
									DishDetail dishDetail = new DishDetail();
									JSONObject dataObject = jsonObject.getJSONObject(Config.KEY_DATA);
									dishDetail.setShopId(dataObject.getString(Config.KEY_SHOPID));
									dishDetail.setDishId(dataObject.getString(Config.KEY_DISH_ID));
									dishDetail.setDishName(dataObject.getString(Config.KEY_DISH_NAME));
									dishDetail.setDishThumb(dataObject.getString(Config.KEY_THUMB));
									dishDetail.setPrice_disp(new BigDecimal(dataObject.getDouble(Config.KEY_PRICE_DISP)).setScale(1, BigDecimal.ROUND_HALF_UP));
									dishDetail.setPack_price(new BigDecimal(dataObject.getDouble(Config.KEY_PACKPRICE)).setScale(1, BigDecimal.ROUND_HALF_UP));
									successCallback.onSuccess(dishDetail);
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
		}, Config.KEY_ACTION, Config.ACTION_DISHDETAIL,
		   Config.KEY_TOKEN, token,
		   Config.KEY_SHOPID, shopId,
		   Config.KEY_DISH_ID, dishId);
	}
	
	public static interface SuccessCallback{
		void onSuccess(DishDetail dish);
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
}
