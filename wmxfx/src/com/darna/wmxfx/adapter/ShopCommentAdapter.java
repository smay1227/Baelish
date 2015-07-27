package com.darna.wmxfx.adapter;

import java.util.ArrayList;
import java.util.List;

import com.darna.wmxfx.R;
import com.darna.wmxfx.bean.ShopPointList;
import com.darna.wmxfx.layout.RoundImageView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShopCommentAdapter extends BaseAdapter{
	private List<ShopPointList> pointList = new ArrayList<ShopPointList>();
	private ShopCommentCell scc;
	private LayoutInflater mLayoutInflater;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	public ShopCommentAdapter(Context mContext) {
		mLayoutInflater = LayoutInflater.from(mContext);
		initImageLoader(mContext);
	}

	@Override
	public int getCount() {
		return pointList.size();
	}

	@Override
	public ShopPointList getItem(int position) {
		return pointList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_shopcomment, parent, false);
			scc = new ShopCommentCell();
			scc.ri_userImage = (RoundImageView) convertView.findViewById(R.id.ri_userImage);
			scc.tv_user = (TextView) convertView.findViewById(R.id.tv_user);
			scc.tv_commenttime = (TextView) convertView.findViewById(R.id.tv_commenttime);
			scc.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
			scc.rb_commentRate = (RatingBar) convertView.findViewById(R.id.rb_commentRate);
			convertView.setTag(scc);
		}else {
			scc = (ShopCommentCell) convertView.getTag();
		}
		ShopPointList pointList = getItem(position);
		if (!pointList.getAlias().equals(null) && !pointList.getAlias().equals("null") && !pointList.getAlias().trim().equals("")) {
			scc.tv_user.setText(pointList.getAlias());
		}
		scc.tv_commenttime.setText(pointList.getEvaluate_time());
		scc.rb_commentRate.setRating(pointList.getPoint());
		scc.tv_comment.setText(pointList.getExperience());
		
		String imageUrl = "http://www.wmxfx.com/" + pointList.getThumb();
		imageLoader.displayImage(imageUrl + "", scc.ri_userImage);
		return convertView;
	}
	
	public void addAll(List<ShopPointList> pointList){
		clear();
		this.pointList = pointList;
		notifyDataSetChanged();
	}
	
	public void clear(){
		pointList.clear();
	}
	
	public class ShopCommentCell{
		RoundImageView ri_userImage;
		TextView tv_user, tv_commenttime, tv_comment;
		RatingBar rb_commentRate;
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
