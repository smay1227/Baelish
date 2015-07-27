package com.darna.wmxfx.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.ShopPoint;
import com.darna.wmxfx.bean.ShopPointList;
import com.darna.wmxfx.bean.ShopPointTotal;

public class NetShopComment {
	public NetShopComment(final Context mContext,final String token, String shopId, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)) {
							if (jsonObject.getBoolean(Config.KEY_LOCATE)) {
								if (successCallback != null) {
									ShopPoint shopPoint = new ShopPoint();
									
									JSONObject dataObject = jsonObject.getJSONObject(Config.KEY_DATA);
									
									ShopPointTotal pointTotal = new ShopPointTotal();
									JSONObject pointTotalObject = dataObject.getJSONObject(Config.KEY_POINT_TOTAL);
									pointTotal.setPointTotal(pointTotalObject.getString(Config.KEY_POINT_TOTAL));
									pointTotal.setTotal(pointTotalObject.getString(Config.KEY_TOTAL));
									shopPoint.setPointTotal(pointTotal);
									
									JSONArray pointListArray = dataObject.getJSONArray(Config.KEY_POINT_LIST);
									List<ShopPointList> shopPointLists = new ArrayList<ShopPointList>();
									ShopPointList pointList;
									JSONObject pointListObject;
									for (int i = 0; i < pointListArray.length(); i++) {
										pointListObject = pointListArray.getJSONObject(i);
										pointList = new ShopPointList();
										pointList.setMobile_phone(pointListObject.getString(Config.KEY_MOBILE_PHONE));
										pointList.setAlias(pointListObject.getString(Config.KEY_ALIAS));
										pointList.setPoint((float)pointListObject.getDouble(Config.KEY_POINT));
										pointList.setEvaluate_time(pointListObject.getString(Config.KEY_EVALUATE_TIME));
										pointList.setExperience(pointListObject.getString(Config.KEY_EXPERIENCE));
										pointList.setThumb(pointListObject.getString(Config.KEY_THUMB));
										shopPointLists.add(pointList);
									}
									shopPoint.setPointList(shopPointLists);
									
									successCallback.onSuccess(shopPoint);
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
		}, Config.KEY_ACTION, Config.ACTION_SHOPCOMMENT,
		   Config.KEY_TOKEN, token,
		   Config.KEY_SHOPID, shopId);
	}
	
	public static interface SuccessCallback{
		void onSuccess(ShopPoint shopPoint);
	}
	
	public static interface FailCallback{
		void onFail(String errorCode);
	}
}
