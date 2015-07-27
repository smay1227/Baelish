package com.darna.wmxfx.net;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.Order;
import com.darna.wmxfx.bean.OrderShop;
import com.darna.wmxfx.bean.OrderVouchers;

public class NetOrderCheck {
	public NetOrderCheck(final Context mContext, final String token, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)){
							if (successCallback != null) {
								JSONObject orderJsonObject = jsonObject.getJSONObject(Config.KEY_DATA);
								Order order = new Order();
								
								List<OrderShop> shops = new ArrayList<OrderShop>();
								OrderShop shop;
								JSONArray shopArray	 = orderJsonObject.getJSONArray(Config.KEY_SHOPLIST);	
								for (int i = 0; i < shopArray.length(); i++) {
									JSONObject shopJsonObject = shopArray.getJSONObject(i);
									shop = new OrderShop();
									shop.setShop_id(shopJsonObject.getString(Config.KEY_SHOPID));
									shop.setShop_name(shopJsonObject.getString(Config.KEY_SHOPNAME));
									shop.setShow_book_way(shopJsonObject.getString(Config.KEY_SHOWBOOKWAY));
									if (shop.getShow_book_way().equals("1") || shop.getShow_book_way().equals("3")) {
										List<String> todayBook = new ArrayList<String>();
										JSONArray todayArray = shopJsonObject.getJSONArray(Config.KEY_TODAYBOOK);
										for (int j = 0; j < todayArray.length(); j++) {
											String value = todayArray.getString(j);
											todayBook.add(value);
										}
										shop.setTodayBook(todayBook);
									}
									List<String> otherBook = new ArrayList<String>();
									JSONArray otherArray = shopJsonObject.getJSONArray(Config.KEY_OTHERBOOK);
									for (int j = 0; j < otherArray.length(); j++) {
										String value = otherArray.getString(j);
										otherBook.add(value);
									}
									shop.setOtherBook(otherBook);
									shops.add(shop);
								}
								order.setShops(shops);
								
								//初始化代金券
								List<OrderVouchers> vouchers = new ArrayList<OrderVouchers>();
								OrderVouchers voucher;
								JSONArray voucherJsonArray = orderJsonObject.getJSONArray(Config.KEY_VOUCHERS);
								for (int i = 0; i < voucherJsonArray.length(); i++) {
									JSONObject voucherJsonObject = voucherJsonArray.getJSONObject(i);
									voucher = new OrderVouchers();
									voucher.setVouchers_id(voucherJsonObject.getString(Config.KEY_VOUCHERSID));
									voucher.setVouchers_type(voucherJsonObject.getString(Config.KEY_VOUCHERSTYPE));
									voucher.setVouchers_name(voucherJsonObject.getString(Config.KEY_VOUCHERSNAME));
									voucher.setVouchers_money(Config.getBigDecimal(voucherJsonObject.getDouble(Config.KEY_VOUCHERSMONEY)));
									vouchers.add(voucher);
								}
								order.setVouchers(vouchers);
								//代金券初始化结束
								
								order.setIntegralist(orderJsonObject.getInt(Config.KEY_INTEGRAL));
								
								order.setUserflg(orderJsonObject.getString(Config.KEY_USERFLG));
								order.setAll_dish_money(Config.getBigDecimal(orderJsonObject.getDouble(Config.KEY_ALLDISHMONEY)));
								order.setAll_delivery_cost(Config.getBigDecimal(orderJsonObject.getDouble(Config.KEYALLDELIVERYCOST)));
								order.setAll_pack_price(Config.getBigDecimal(orderJsonObject.getDouble(Config.KEY_ALLPACKPRICE)));
								order.setSub_money(Config.getBigDecimal(orderJsonObject.getDouble(Config.KEY_SUBMONEY)));
								successCallback.onSuccess(order);
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
		}, Config.KEY_ACTION, Config.ACTION_ORDERCHECKOUT,
		   Config.KEY_TOKEN, token);
	}
	
	public List<String> toList(JSONObject jsonObject){
		List<String> data = new ArrayList<String>();
		Iterator<String> it = jsonObject.keys();
		try {
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				String value;
				value = (String) jsonObject.get(key);
				data.add(value);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static interface SuccessCallback{
		void onSuccess(Order order);
	}
	public static interface FailCallback{
		void onFail(String errorCode);
	}
}
