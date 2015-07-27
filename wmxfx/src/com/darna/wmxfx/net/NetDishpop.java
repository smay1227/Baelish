package com.darna.wmxfx.net;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.DishDetailInfo;
import com.darna.wmxfx.bean.DishDetailInfoClassify;

public class NetDishpop {

	public NetDishpop(final Context mContext, final String token, String shopId,
			String dishId, final SuccessCallback successCallback,
			final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL,HttpMethod.POST, new NetConnection.SuccessCallback() {
					@Override
					public void onSuccess(String result) {
						try {
							JSONObject jsonObject = new JSONObject(result);
							if (jsonObject.getBoolean(Config.KEY_STATUS)) {
								if (jsonObject.getString(Config.KEY_TOKEN).equals(token)){
									if (jsonObject.getBoolean(Config.KEY_LOCATE)) {
										if (successCallback != null) {
											DishDetailInfo dishDetailInfo = new DishDetailInfo();
											JSONObject dataObject = jsonObject.getJSONObject("data");
											dishDetailInfo.setShop_id(dataObject.getString(Config.KEY_SHOPID));
											dishDetailInfo.setDish_id(dataObject.getString(Config.KEY_DISH_ID));
											dishDetailInfo.setDish_name(dataObject.getString(Config.KEY_DISH_NAME));
											dishDetailInfo.setDish_price(new BigDecimal(dataObject.getDouble(Config.KEY_PRICE_DISP)).setScale(1,BigDecimal.ROUND_HALF_UP));
											dishDetailInfo.setPack_price(new BigDecimal(dataObject.getDouble(Config.KEY_PACK_PRICE)).setScale(1,BigDecimal.ROUND_HALF_UP));
											dishDetailInfo.setHas_classify(dataObject.getBoolean(Config.KEY_HAS_CLASSIFY));
											if (dataObject.getBoolean(Config.KEY_HAS_CLASSIFY)) {
												JSONArray jsonArray = dataObject.getJSONArray(Config.KEY_CLASSIFY_INFO);
												List<DishDetailInfoClassify> classifyInfoes = new ArrayList<DishDetailInfoClassify>();
												for (int i = 0; i < jsonArray.length(); i++) {
													JSONObject classifyJsonObject = jsonArray.getJSONObject(i);
													classifyInfoes.add(new DishDetailInfoClassify(classifyJsonObject.getString(Config.KEY_SHOPID),
																	classifyJsonObject.getString(Config.KEY_DISH_ID),
																	classifyJsonObject.getString(Config.KEY_SIZE_CODE),
																	classifyJsonObject.getString(Config.KEY_SPEC_CODE),
																	classifyJsonObject.getString(Config.KEY_TASTE_CODE),
																	new BigDecimal(
																			classifyJsonObject.getDouble(Config.KEY_DISH_PRICE)).setScale(1,BigDecimal.ROUND_HALF_UP),
																	new BigDecimal(
																			classifyJsonObject.getDouble(Config.KEY_PACK_PRICE)).setScale(1,BigDecimal.ROUND_HALF_UP),
																	new BigDecimal(
																			classifyJsonObject.getDouble(Config.KEY_PRICE_NEW)).setScale(1,BigDecimal.ROUND_HALF_UP)));
												}
												dishDetailInfo.setClassify_info(classifyInfoes);
											}
											
											Map<String, String> size_dict = new HashMap<String, String>();
											Map<String, String> spec_dict = new HashMap<String, String>();
											Map<String, String> taste_dict = new HashMap<String, String>();

											// dataObject.

											if (!dataObject.isNull(Config.KEY_SIZE_DICT)) {
												size_dict = Config.toHashMap(dataObject.getJSONObject(Config.KEY_SIZE_DICT));
												// System.out.println("值不为空");
											}

											if (!dataObject.isNull(Config.KEY_SPEC_DICT)) {
												spec_dict = Config.toHashMap(dataObject.getJSONObject(Config.KEY_SPEC_DICT));
												// System.out.println("值为空");
											}

											if (!dataObject.isNull(Config.KEY_TASTE_DICT)) {
												taste_dict = Config.toHashMap(dataObject.getJSONObject(Config.KEY_TASTE_DICT));
												// System.out.println("值为空");
											}

											dishDetailInfo.setSize_dict(size_dict);
											dishDetailInfo.setSpec_dict(spec_dict);
											dishDetailInfo.setTaste_dict(taste_dict);

											successCallback.onSuccess(dishDetailInfo);
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
				}, Config.KEY_ACTION, Config.ACTION_DISHINFO,
				Config.KEY_TOKEN, token,
				Config.KEY_SHOPID, shopId, 
				Config.KEY_DISH_ID, dishId);
	}

	

	public static interface SuccessCallback {
		void onSuccess(DishDetailInfo dish);
	}

	public static interface FailCallback {
		void onFail(String errorCode);
	}

}
