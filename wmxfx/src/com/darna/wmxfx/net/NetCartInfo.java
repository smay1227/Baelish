package com.darna.wmxfx.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.API_Cart;
import com.darna.wmxfx.bean.API_CartDish;
import com.darna.wmxfx.bean.API_CartShop;


public class NetCartInfo {
	public NetCartInfo(final Context mContext, final String token, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)) {
							if (jsonObject.getBoolean(Config.KEY_LOCATE)) {
								if (successCallback != null) {
									API_Cart cartInfo = new API_Cart();
									JSONObject dataObject = jsonObject.getJSONObject(Config.KEY_DATA);
										cartInfo.setIs_new_user(dataObject.getBoolean(Config.KEY_ISNEWUSER));
										cartInfo.setCart_total(Config.getBigDecimal(dataObject.getDouble(Config.KEY_CART_TOTAL)));
										JSONArray shoplistJsonArray = dataObject.getJSONArray(Config.KEY_SHOP_LIST);
										List<API_CartShop> shoplist = new ArrayList<API_CartShop>();
										JSONObject shoplistJsonObject;
										for (int i = 0; i < shoplistJsonArray.length(); i++) {
											shoplistJsonObject = shoplistJsonArray.getJSONObject(i);
											API_CartShop shop = new API_CartShop();
											shop.setShop_id(shoplistJsonObject.getString(Config.KEY_SHOPID));
											shop.setShop_name(shoplistJsonObject.getString(Config.KEY_SHOPNAME));
											shop.setDis_range(shoplistJsonObject.getDouble(Config.KEY_DIS_RANGE));
											shop.setShop_item_money(Config.getBigDecimal(shoplistJsonObject.getDouble(Config.KEY_SHOP_ITEM_MONEY)));
											shop.setShop_all_pack_price(Config.getBigDecimal(shoplistJsonObject.getDouble(Config.KEY_SHOPALLPACKPRICE)));
											shop.setDelivery_price(Config.getBigDecimal(shoplistJsonObject.getDouble(Config.KEY_DELIVERYPRICE)));
											shop.setActivity_flg(shoplistJsonObject.getString(Config.KEY_ACTIVITYFLG));
											shop.setMyself_param(Config.getBigDecimal(shoplistJsonObject.getDouble(Config.KEY_MYSELFPARAM)));
											shop.setShop_real_price(Config.getBigDecimal(shoplistJsonObject.getDouble(Config.KEY_SHOPREALPRICE)));
											shop.setIntroduce(shoplistJsonObject.getString(Config.KEY_INTRODUCE));
											JSONArray dishlistJsonArray = shoplistJsonObject.getJSONArray(Config.KEY_DISH_LIST);
											List<API_CartDish> dishlist = new ArrayList<API_CartDish>();
											JSONObject dishlistJsonObject;
											for (int j = 0; j < dishlistJsonArray.length(); j++) {
												dishlistJsonObject = dishlistJsonArray.getJSONObject(j);
												API_CartDish dish = new API_CartDish();
												dish.setDish_id(dishlistJsonObject.getString(Config.KEY_DISH_ID));
												dish.setDish_name(dishlistJsonObject.getString(Config.KEY_DISH_NAME));
												dish.setDish_main_name(dishlistJsonObject.getString(Config.KEY_DISHMAINNAME));
												dish.setDish_price(Config.getBigDecimal(dishlistJsonObject.getDouble(Config.KEY_DISH_PRICE)));
												dish.setThumb(dishlistJsonObject.getString(Config.KEY_THUMB));
												dish.setShop_id(dishlistJsonObject.getString(Config.KEY_SHOPID));
												dish.setNumber(dishlistJsonObject.getInt(Config.KEY_NUMBER));
												dish.setShop_name(dishlistJsonObject.getString(Config.KEY_SHOPNAME));
												dish.setDish_price_subtotal(Config.getBigDecimal(dishlistJsonObject.getDouble(Config.KEY_DISHPRICESUBTOTAL)));
												dish.setPack_price(Config.getBigDecimal(dishlistJsonObject.getDouble(Config.KEY_PACK_PRICE)));
												dish.setAll_pack_price(Config.getBigDecimal(dishlistJsonObject.getDouble(Config.KEY_ALLPACKPRICE)));
												dish.setAttr_code(dishlistJsonObject.getString(Config.KEY_ATTRCODE));
												dish.setAttr_name(dishlistJsonObject.getString(Config.KEY_ATTRNAME));
												dish.setDistance(dishlistJsonObject.getDouble(Config.KEY_DISTANCE));
												dish.setActivity_type(dishlistJsonObject.getString(Config.KEY_ACTIVITYTYPE));
												dishlist.add(dish);
											}
											shop.setDish_list(dishlist);
											shoplist.add(shop);
										}
										cartInfo.setShop_list(shoplist);
									
									successCallback.onSuccess(cartInfo);
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
		}, Config.KEY_ACTION, Config.ACTION_CARTINFO,
		   Config.KEY_TOKEN, token);
	}
	
	public static interface SuccessCallback{
		void onSuccess(API_Cart CartInfo);
	}
	public static interface FailCallback{
		void onFail(String errorCode);
	}
}
