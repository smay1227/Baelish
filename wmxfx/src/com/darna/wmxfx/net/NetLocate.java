package com.darna.wmxfx.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.darna.wmxfx.Config;

public class NetLocate {
	public NetLocate(String token, String posTitle, String posAddress,
			String posX, String posY, final SuccessCallback successCallback,
			final FailCallback failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						if (successCallback != null) {
							successCallback.onSuccess();
						}
					}else {
						if (failCallback != null) {
							failCallback.onFail();
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
					if (failCallback != null) {
						failCallback.onFail();
					}
				}
			}
		}, new NetConnection.FailCallback() {
			@Override
			public void onFail() {
				if (failCallback != null) {
					failCallback.onFail();
				}
			}
		}, Config.KEY_ACTION, Config.ACTION_LOCATE,
		   Config.KEY_TOKEN, token,
		   Config.KEY_POSTITLE, posTitle,
		   Config.KEY_POSADDRESS, posAddress,
		   Config.KEY_POSX, posX,
		   Config.KEY_POSY, posY);
	}

	public static interface SuccessCallback {
		void onSuccess();
	}

	public static interface FailCallback {
		void onFail();
	}

}
