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
import com.darna.wmxfx.bean.DishType;

public class FrgDishLeftAdapter extends BaseAdapter {
	List<DishType> data = new ArrayList<DishType>();
	private LayoutInflater mLayoutInflater;
	LeftHolder leftHolder;
	Context mContext;
	
	public FrgDishLeftAdapter(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
		this.mContext = context;
	}
	
	public void add(List<DishType> data){
		clear();
		this.data = data;
		notifyDataSetChanged();
	}
	
	public void clear(){
		this.data.clear();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public DishType getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_dishleft, parent, false);
			leftHolder = new LeftHolder();
			leftHolder.tv_dishleft = (TextView) convertView.findViewById(R.id.tv_dishleft);
			leftHolder.iv_dishleft = (ImageView) convertView.findViewById(R.id.iv_dishleft);
			convertView.setTag(leftHolder);
		}else {
			leftHolder = (LeftHolder) convertView.getTag();
		}
		DishType type = getItem(position);
		leftHolder.tv_dishleft.setText(type.getTypeName());
		if (type.getTypeFlag()) {
			leftHolder.tv_dishleft.setTextColor(mContext.getResources().getColor(R.color.used));
			leftHolder.iv_dishleft.setImageResource(R.drawable.dishleft);
			
		}else {
			leftHolder.tv_dishleft.setTextColor(mContext.getResources().getColor(R.color.black));
			leftHolder.iv_dishleft.setImageResource(R.drawable.undishleft);
		}
		return convertView;
	}
	
	public class LeftHolder{
		TextView tv_dishleft;
		ImageView iv_dishleft;
	}

}
