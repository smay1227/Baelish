package com.darna.wmxfx.dialog;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.atys.Aty_Order;
import com.darna.wmxfx.bean.DeliveryTime;
import com.darna.wmxfx.utils.PinnedHeaderListView;
import com.darna.wmxfx.utils.SectionedBaseAdapter;

public class DialogDeliveryTime extends Activity {
	List<String> todayBook = new ArrayList<String>();
	List<String> otherBook = new ArrayList<String>();
	String showBookWay;
	RelativeLayout rl_deliverypop, rl_head;
	ListView leftListView;
	PinnedHeaderListView rightListView;
	List<DeliveryTime> deliveryTimes = new ArrayList<DeliveryTime>();
	DeliveryTimeLeftAdapter deliveryTimeLeftAdapter;
	DeliveryTimeRightAdapter deliveryTimeRightAdapter;
	String day = null, time = null;
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_deliverytime);
		showBookWay = getIntent().getStringExtra(Config.KEY_SHOWBOOKWAY);
		if (showBookWay.equals("1") || showBookWay.equals("3")) {
			todayBook = getIntent().getStringArrayListExtra(Config.KEY_TODAYBOOK);
		}
		otherBook = getIntent().getStringArrayListExtra(Config.KEY_OTHERBOOK);
		
		day = getIntent().getStringExtra(Config.KEY_DAY);
		time = getIntent().getStringExtra(Config.KEY_TIME);
		
		init();
		
		gatherData();
		
	}
	
	private void gatherData() {
		DeliveryTime deliveryTime;
		
		deliveryTime = new DeliveryTime();
		deliveryTime.setDayType("立即送餐");
		List<String> ptime = new ArrayList<String>();
		ptime.add("立即送餐");
		deliveryTime.setTime(ptime);
		deliveryTimes.add(deliveryTime);
		
		if (showBookWay.equals("1") || showBookWay.equals("3")) {
			deliveryTime = new DeliveryTime();
			deliveryTime.setDayType("今天");
			deliveryTime.setTime(todayBook);
			deliveryTimes.add(deliveryTime);
		}
		
		deliveryTime = new DeliveryTime();
		deliveryTime.setDayType("明天");
		deliveryTime.setTime(otherBook);
		deliveryTimes.add(deliveryTime);
		
		List<String> tommorrowDays = otherBook;
		
		deliveryTime = new DeliveryTime();
		deliveryTime.setDayType("后天");
		deliveryTime.setTime(tommorrowDays);
		deliveryTimes.add(deliveryTime);
		deliveryTimeLeftAdapter.notifyDataSetChanged();
		deliveryTimeRightAdapter.notifyDataSetChanged();
		int position = 0;
		int rightPosition = 0;
		for (int i = 0; i < deliveryTimes.size(); i++) {
			if (deliveryTimes.get(i).getDayType().equals(day)) {
				position = i;
				for (int j = 0; j < deliveryTimes.get(i).getTime().size(); j++) {
					if(deliveryTimes.get(i).getTime().get(j).equals(time)){
						rightPosition = j;
					}
				}
			}
		}
		
		int rightSection = 0;
		for(int i=0;i<position;i++){
			rightSection += deliveryTimeRightAdapter.getCountForSection(i);
		}
		rightSection += rightPosition;
		rightListView.setSelection(rightSection);
		
	}

	private void init() {
		rl_deliverypop = (RelativeLayout) findViewById(R.id.rl_deliverypop);
		rl_head = (RelativeLayout) findViewById(R.id.rl_head);
		rl_deliverypop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		rl_head.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		leftListView = (ListView) findViewById(R.id.left_listview);
		rightListView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
		deliveryTimeLeftAdapter = new DeliveryTimeLeftAdapter();
		deliveryTimeRightAdapter = new DeliveryTimeRightAdapter();
		leftListView.setAdapter(deliveryTimeLeftAdapter);
		rightListView.setAdapter(deliveryTimeRightAdapter);
	}

	@Override
	public void finish() {
		Intent intent = new Intent(DialogDeliveryTime.this, Aty_Order.class);
		intent.putExtra(Config.KEY_DAY, day);
		intent.putExtra(Config.KEY_TIME, time);
		setResult(Config.DIALOG_DELIVERYTIME, intent);
		super.finish();
	}
	
	public class DeliveryTimeLeftAdapter extends BaseAdapter{
		LeftHolder leftHolder;

		@Override
		public int getCount() {
			return deliveryTimes.size();
		}

		@Override
		public String getItem(int position) {
			return deliveryTimes.get(position).getDayType();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_deliveryleft, parent, false);
				leftHolder = new LeftHolder();
				leftHolder.tv_daytype = (TextView) convertView.findViewById(R.id.tv_daytype);
				leftHolder.iv_day = (ImageView) convertView.findViewById(R.id.iv_day);
				convertView.setTag(leftHolder);
			}else {
				leftHolder = (LeftHolder) convertView.getTag();
			}
			String type = getItem(position);
			leftHolder.tv_daytype.setText(type);
			if (type.equals(day)) {
				leftHolder.tv_daytype.setTextColor(getResources().getColor(R.color.used));
				leftHolder.iv_day.setImageResource(R.drawable.dishleft);
			}else {
				leftHolder.tv_daytype.setTextColor(getResources().getColor(R.color.black));
				leftHolder.iv_day.setImageResource(R.drawable.undishleft);
			}
			return convertView;
		}
		
		public class LeftHolder{
			TextView tv_daytype;
			ImageView iv_day;
		}
		
	}
	
	public class DeliveryTimeRightAdapter extends SectionedBaseAdapter{

		SectionHeadItem sectionHeadItem;
		SectionItem sectionItem;
		
		@Override
		public String getItem(int section, int position) {
			return deliveryTimes.get(section).getTime().get(position);
		}
		
		public String getSection(int section){
			return deliveryTimes.get(section).getDayType();
		}

		@Override
		public long getItemId(int section, int position) {
			return position;
		}

		@Override
		public int getSectionCount() {
			return deliveryTimes.size();
		}

		@Override
		public int getCountForSection(int section) {
			return deliveryTimes.get(section).getTime().size();
		}

		@Override
		public View getItemView(int section, int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_deliverytimeitem, parent, false);
				sectionItem = new SectionItem();
				sectionItem.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				sectionItem.iv_time = (ImageView) convertView.findViewById(R.id.iv_time);
				sectionItem.rl_time = (RelativeLayout) convertView.findViewById(R.id.rl_time);
				convertView.setTag(sectionItem);
			}else {
				sectionItem = (SectionItem) convertView.getTag();
			}
			final String privatetime = getItem(section, position);
			final String privateday = getSection(section);
			sectionItem.tv_time.setText(privatetime);
			if (privateday.equals(day) && privatetime.equals(time)) {
				sectionItem.tv_time.setTextColor(getResources().getColor(R.color.used));
				sectionItem.iv_time.setVisibility(View.VISIBLE);
			}else {
				sectionItem.tv_time.setTextColor(getResources().getColor(R.color.black));
				sectionItem.iv_time.setVisibility(View.GONE);
			}
			sectionItem.rl_time.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					day = privateday;
					time = privatetime;
					deliveryTimeRightAdapter.notifyDataSetChanged();
					deliveryTimeLeftAdapter.notifyDataSetChanged();
				}
			});
			return convertView;
		}

		@Override
		public View getSectionHeaderView(int section, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_deliverytimehead, parent, false);
				sectionHeadItem = new SectionHeadItem();
				sectionHeadItem.tv_day = (TextView) convertView.findViewById(R.id.tv_day);
				convertView.setTag(sectionHeadItem);
			}else {
				sectionHeadItem = (SectionHeadItem) convertView.getTag();
			}
			String type = getSection(section);
			sectionHeadItem.tv_day.setText(type);
			
			return convertView;
		}
		
		public class SectionHeadItem{
			TextView tv_day;
		}
		
		public class SectionItem{
			TextView tv_time;
			ImageView iv_time;
			RelativeLayout rl_time;
		}
		
	}
	
}
