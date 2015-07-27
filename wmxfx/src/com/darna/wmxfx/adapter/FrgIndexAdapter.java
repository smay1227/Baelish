package com.darna.wmxfx.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.darna.wmxfx.R;
import com.darna.wmxfx.bean.Shop;
import com.darna.wmxfx.cell.ShopListCell;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class FrgIndexAdapter extends BaseAdapter {
	
	public FrgIndexAdapter(Context context){
		//this.context = context;
		mLayoutInflater = LayoutInflater.from(context);
		initImageLoader(context);
	}
	
	@Override
	public int getCount() {
		return shops.size();
	}

	@Override
	public Shop getItem(int position) {
		return shops.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_shoprecommand, parent, false);
			shopListCell = new ShopListCell();
			shopListCell.tv_shopname = (TextView) convertView.findViewById(R.id.tv_shopname);
			shopListCell.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
			shopListCell.iv_shopimage = (ImageView) convertView.findViewById(R.id.iv_shopimage);
			convertView.setTag(shopListCell);
		}else {
			shopListCell = (ShopListCell) convertView.getTag();
		}
		Shop shop = getItem(position);
		shopListCell.tv_shopname.setText(shop.getShop_name());
		shopListCell.tv_distance.setText(shop.getDistance() +"公里   "+ shop.getDeli_time() + "送达");
		
		String shopImageUrl = "http://www.wmxfx.com/images/shop/" + shop.getShop_thumb().replaceAll("images/shop/", "");
		imageLoader.displayImage(shopImageUrl + "", shopListCell.iv_shopimage);
		
		return convertView;
	}
	
	public void add(List<Shop> shops){
		clear();
		this.shops = shops;
		notifyDataSetChanged();
	}
	
	public void clear(){
		shops.clear();
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
	
	private List<Shop> shops = new ArrayList<Shop>();
	private ShopListCell shopListCell;
	private LayoutInflater mLayoutInflater;
	//Context context;
	private ImageLoader imageLoader = ImageLoader.getInstance();
}
