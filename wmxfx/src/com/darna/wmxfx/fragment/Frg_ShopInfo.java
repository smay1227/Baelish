package com.darna.wmxfx.fragment;

import java.util.Map;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.bean.ShopInfo;
import com.darna.wmxfx.layout.RoundImageView;
import com.darna.wmxfx.net.NetLocate;
import com.darna.wmxfx.net.NetShopInfo;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class Frg_ShopInfo extends Fragment{
	View view;
	ShopInfo shopInfo;
	RoundImageView ri_shopImage;
	TextView tv_shopName, tv_shopRate, tv_distancenum, tv_deliverycostnum, tv_averagenum, 
				shoptime, shoploca, shopactivity, shopvoice;
	RatingBar rb_shopRate;
	Boolean initFinish = false, getdataFinish = false;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frg_shopinfo, container, false);
			init();
		}
		ViewGroup parent = (ViewGroup)view.getParent();  
        if(parent != null) {  
            parent.removeView(view);  
        }
		return view;
	}
	
	private void init() {
		ri_shopImage = (RoundImageView) view.findViewById(R.id.ri_shopImage);
		tv_shopName = (TextView) view.findViewById(R.id.tv_shopName);
		rb_shopRate = (RatingBar) view.findViewById(R.id.rb_shopRate);
		tv_shopRate = (TextView) view.findViewById(R.id.tv_shopRate);
		tv_distancenum = (TextView) view.findViewById(R.id.tv_distancenum);
		tv_deliverycostnum = (TextView) view.findViewById(R.id.tv_deliverycostnum);
		tv_averagenum = (TextView) view.findViewById(R.id.tv_averagenum);
		shoptime = (TextView) view.findViewById(R.id.shoptime);
		shoploca = (TextView) view.findViewById(R.id.shoploca);
		shopactivity = (TextView) view.findViewById(R.id.shopactivity);
		shopvoice = (TextView) view.findViewById(R.id.shopvoice);
		initFinish = true;
		getShopInfo();
	}

	private void setPage() {
			tv_shopName.setText(shopInfo.getShop_name());
			rb_shopRate.setRating(shopInfo.getAverage_point());
			tv_shopRate.setText(shopInfo.getAverage_point() + "");
			tv_distancenum.setText(shopInfo.getDistance() + "公里");
			tv_deliverycostnum.setText("￥" + shopInfo.getDeliverycost());
			tv_averagenum.setText("￥" + shopInfo.getAverage_cost());
			shoptime.setText("营业时间：" + shopInfo.getBusinesshour());
			shoploca.setText("地址：" + shopInfo.getAddress());
			String shopDesc = shopInfo.getActivitydesc();
			if (shopDesc.equals(null) || shopDesc.equals("") || shopDesc.equals("null")) {
				shopDesc = "暂无";
			}
			String voice = shopInfo.getNotice();
			if (voice.equals(null) || voice.equals("")) {
				voice = "暂无";
			}
			
			shopactivity.setText("活动：" + shopDesc);
			shopvoice.setText("公告：" + voice);
			
			String shopImageUrl = "http://www.wmxfx.com/images/shop/" + shopInfo.getThumb();
			imageLoader.displayImage(shopImageUrl + "", ri_shopImage);
	}

	public void addData(ShopInfo shopInfo){
		this.shopInfo = shopInfo;
		getdataFinish = true;
		setPage();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initImageLoader(getActivity());
	}

	private void getShopInfo() {
		new NetShopInfo(getActivity(),Config.getCachedToken(getActivity()), Config.getCachedShopId(getActivity()), new NetShopInfo.SuccessCallback() {
			@Override
			public void onSuccess(ShopInfo shopInfo) {
				addData(shopInfo);
			}
		}, new NetShopInfo.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("获取购物车信息失败");
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
					setAddressInSession();
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession();
				}
			}
		});
	}
	
	private void setAddressInSession() {
		Map<String, String> poiMap = Config.getCachePOI(getActivity());
		new NetLocate(Config.getCachedToken(getActivity()), poiMap.get(Config.KEY_POSTITLE), 
				poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
					@Override
					public void onSuccess() {
						getShopInfo();
					}
				}, new NetLocate.FailCallback() {
					@Override
					public void onFail() {
						System.out.println("上传缓存地址到服务器失败");
					}
				});
	}
	
	public static void initImageLoader(Context context) {  
        // This configuration tuning is custom. You can tune every option, you  
        // may tune some of them,  
        // or you can create default configuration by  
        // ImageLoaderConfiguration.createDefault(this);  
        // method.  
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove  
                                                                                                                                                                                                                                                                                                // for  
                                                                                                                                                                                                                                                                                                // release  
                                                                                                                                                                                                                                                                                                // app  
                .build();  
        // Initialize ImageLoader with configuration.  
        ImageLoader.getInstance().init(config);  
    }
	
}
