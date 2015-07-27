package com.darna.wmxfx.utils;

import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.darna.wmxfx.Config;
import com.darna.wmxfx.MainActivity;
import com.darna.wmxfx.net.NetLocate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class BaiduUtil {
	Context mContext;
	LocationClient mLocClient;
	public MyLocationListenner myListener  = new MyLocationListenner();
	boolean isFirstLoc = true;// 是否首次定位
	
	public BaiduUtil(Context context) {
		this.mContext = context;
		System.out.println("------------------>baiduContext<----------------------");
	}
	
	public void start(){
		System.out.println("------------------>start<----------------------");
		mLocClient = new LocationClient(mContext);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setIsNeedAddress(true);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}
	
	public void stop(){
		System.out.println("------------------>stop<----------------------");
		mLocClient.stop();
	}
	
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			System.out.println("定位的回调函数");
			// map 销毁后不在处理新接收的位置
			if (location == null) return;
			if (isFirstLoc) {
				isFirstLoc = false;
				/*System.out.println("经度" + location.getLatitude());
				System.out.println("纬度" + location.getLongitude());
				System.out.println("Addrstr" + location.getAddrStr() + " altitude" + location.getAltitude() + 
						" citycode" + location.getCityCode() + " direction" + location.getDirection() + " province" + location.getProvince()+
						            "  street" + location.getStreet() + "　time" + location.getTime()+ 
						           " bulidId"+ location.getBuildingID() + " address" + location.getAddress() +
						           " country" + location.getCountry() + " direction" + location.getDirection() + 
						           "city" + location.getCity());*/
			 String addrStr = location.getAddrStr();
			 String headStr = location.getCountry() + location.getProvince() + location.getCity();
			 String posTitle = addrStr.replace(headStr, "");
			 String posAddress = location.getStreet();
			 String posX =  String.valueOf(location.getLongitude());
			 System.out.println("posXFirst: " + posX);
			 String posY = String.valueOf(location.getLatitude());
			 System.out.println("posYFirst:" + posY);
			 
			 Config.cachePOI(mContext, posTitle, posAddress, posX, posY);
			 Map<String, String> poiMap = Config.getCachePOI(mContext);
				new NetLocate(Config.getCachedToken(mContext), poiMap.get(Config.KEY_POSTITLE), 
						poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
							@Override
							public void onSuccess() {
								mContext.startActivity(new Intent(mContext, MainActivity.class));
								((Activity) mContext).finish();
							}
						}, new NetLocate.FailCallback() {
							@Override
							public void onFail() {
								System.out.println("上传缓存地址到服务器失败");
							}
						});
			}
		}
	}
}
