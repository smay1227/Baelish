package com.darna.wmxfx.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.UserAddress;

public class NetUserAddress {
	public NetUserAddress(final Context mContext, final String token, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (jsonObject.getString(Config.KEY_TOKEN).equals(token)){
							List<UserAddress> addresses = new ArrayList<UserAddress>();
							JSONArray addressJsonArray = jsonObject.getJSONArray(Config.KEY_DATA);
							JSONObject addressJsonObject;
							UserAddress address;
							for (int i = 0; i < addressJsonArray.length(); i++) {
								addressJsonObject = addressJsonArray.getJSONObject(i);
							    address = new UserAddress();
							    address.setUser_name(addressJsonObject.getString(Config.KEY_USERNAME));
							    address.setD02011(addressJsonObject.getString(Config.KEY_D02011));
							    address.setConsignee(addressJsonObject.getString(Config.KEY_CONSIGNEE));
							    address.setArea_address(addressJsonObject.getString(Config.KEY_AREAADDRESS));
							    address.setDetailed_address(addressJsonObject.getString(Config.KEY_DETAILEDADDRESS));
							    address.setTel(addressJsonObject.getString(Config.KEY_TEL));
							    address.setPosX(addressJsonObject.getString(Config.KEY_POSX));
							    address.setPosY(addressJsonObject.getString(Config.KEY_POSY));
							    address.setDefaultFlg(addressJsonObject.getString(Config.KEY_DEFAULTFLG));
							    address.setStreet_address(addressJsonObject.getString(Config.KEY_STREETADDRESS));
							    addresses.add(address);
							}
							successCallback.onSuccess(addresses);
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
		}, Config.KEY_ACTION, Config.ACTION_USERADDRESS,
		   Config.KEY_TOKEN, token);
	}
	
	public static interface SuccessCallback{
		void onSuccess(List<UserAddress> addresses);
	}
	public static interface FailCallback{
		void onFail(String errorCode);
	}
}
