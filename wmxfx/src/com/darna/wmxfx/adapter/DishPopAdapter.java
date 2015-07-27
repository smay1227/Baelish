package com.darna.wmxfx.adapter;

import java.util.ArrayList;
import java.util.List;

import com.darna.wmxfx.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DishPopAdapter extends BaseAdapter {
	
	List<String> data = new ArrayList<String>();
	DishPopCell dpc;
	LayoutInflater mLayoutInflater;
	Context mContext;
	String code;
	
	public DishPopAdapter(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
		mContext = context;
	}
	
	public void add(List<String> data, String code){
		clear();
		this.data = data;
		this.code = code;
		notifyDataSetChanged();
	}
	
	public void clear(){
		data = null;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public String getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_dishpop, parent, false);
			dpc = new DishPopCell();
			dpc.tv_attrName = (TextView) convertView.findViewById(R.id.tv_attrName);
			dpc.iv_choose = (ImageView) convertView.findViewById(R.id.iv_choose);
			convertView.setTag(dpc);
		}else {
			dpc = (DishPopCell) convertView.getTag();
		}
		String attrName = getItem(position);
		dpc.tv_attrName.setText(attrName);
		if (attrName.equals(code)) {
			dpc.tv_attrName.setTextColor(mContext.getResources().getColor(R.color.bottom_backcolor));
			dpc.iv_choose.setVisibility(View.VISIBLE);
		}else {
			dpc.tv_attrName.setTextColor(mContext.getResources().getColor(R.color.light));
			dpc.iv_choose.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	public class DishPopCell{
		TextView tv_attrName;
		ImageView iv_choose;
	}

}
