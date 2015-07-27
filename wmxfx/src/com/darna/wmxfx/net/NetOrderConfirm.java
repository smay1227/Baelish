package com.darna.wmxfx.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.bean.OrderAmount;


public class NetOrderConfirm {
	
	public NetOrderConfirm(Context mContext, String token, String pay_radio, String d02011_v, String full_voucher_id, String use_integral,
			               String sub_post, final SuccessCallback successCallback, final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (successCallback != null) {
							JSONObject dataObject = jsonObject.getJSONObject(Config.KEY_DATA);
							OrderAmount orderAmount = new OrderAmount();
							orderAmount.setSn(dataObject.getString(Config.KEY_ORDERSN));
							orderAmount.setAmount(dataObject.getInt(Config.KEY_AMOUNT));
							successCallback.onSuccess(orderAmount);
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
		}, Config.KEY_ACTION, Config.ACTION_CREATEORDER,
		   Config.KEY_TOKEN, token,
		   Config.KEY_PAYRADIO, pay_radio,
		   Config.KEY_D02011_V, d02011_v,
		   Config.KEY_FULLVOUCHERID, full_voucher_id,
		   Config.KEY_USEINTEGRAL, use_integral,
		   Config.KEY_SUBPOST, sub_post);
	}
	
	public static interface SuccessCallback{
		void onSuccess(OrderAmount orderAmount);
	}
	public static interface FailCallback{
		void onFail(String errorCode);
	}
	
}
