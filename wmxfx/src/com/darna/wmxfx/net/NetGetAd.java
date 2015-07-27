package com.darna.wmxfx.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.darna.wmxfx.Config;

public class NetGetAd {
	public NetGetAd(final SuccessCallback successCallback, final FailCallbck failCallback) {
		new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject jsonObject = new JSONObject(result);
					if (jsonObject.getBoolean(Config.KEY_STATUS)) {
						Map<String, String> aDMap = new HashMap<String, String>();
						aDMap = Config.toHashMap(jsonObject.getJSONObject(Config.KEY_DATA));
						List<String> adList = new ArrayList<String>();
						Iterator<Entry<String, String>> iterator = aDMap.entrySet().iterator();
						while (iterator.hasNext()) {
							Map.Entry<String, String> entry = iterator.next();
							adList.add("http://www.wmxfx.com/images/AD/index/" + entry.getValue());
						}
						for (int i = 0; i < aDMap.size(); i++) {
							aDMap.get(i + "");
						}
						for (int i = 0; i < adList.size(); i++) {
							System.out.println(adList.get(i));
						}
						successCallback.onSuccess(adList);
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
		}, Config.KEY_ACTION, Config.ACTION_GETAD);
	}
	
	public static interface SuccessCallback{
		void onSuccess(List<String> ads);
	}
	public static interface FailCallbck{
		void onFail(String errorCode);
	}
}
