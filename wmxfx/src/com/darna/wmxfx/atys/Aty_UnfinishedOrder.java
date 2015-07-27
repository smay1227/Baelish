package com.darna.wmxfx.atys;

import java.util.ArrayList;
import java.util.List;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.bean.API_CartDish;
import com.darna.wmxfx.bean.API_CartShop;
import com.darna.wmxfx.net.NetHistoryOrderInfo;
import com.darna.wmxfx.net.NetOrderCancel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Aty_UnfinishedOrder extends Activity{
	
	List<API_CartShop> mOrder = new ArrayList<API_CartShop>();
	String order_sn;
	ExpandableListView elv_list;
	OrderAdapter orderAdapter;
	LinearLayout ll_cancelpay;
	TextView tv_deliverycost, tv_newuser, tv_sn, tv_ordertime, tv_payway, tv_phone, tv_address;
	Button btn_pay;
	ImageView iv_numline, iv_rleft;
	View head, foot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_unfinishedorder);
		elv_list = (ExpandableListView) findViewById(R.id.elv_list);
		orderAdapter = new OrderAdapter();
		elv_list.setAdapter(orderAdapter);
		
		order_sn = getIntent().getStringExtra(Config.KEY_ORDERSN);
		
		iv_rleft = (ImageView) findViewById(R.id.iv_rleft);
		
		iv_rleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		//设置不能关闭
		elv_list.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				return true;
			}
		});
		
		getData();
	}
	
	private void initView() {

		if(foot == null){
			foot = LayoutInflater.from(this).inflate(R.layout.layout_unfinishorderfootforpay, null, false);
			elv_list.addFooterView(foot);
		}
		
		
		tv_deliverycost = (TextView) foot.findViewById(R.id.tv_deliverycost);
		tv_newuser = (TextView) foot.findViewById(R.id.tv_newuser);
		tv_sn = (TextView) foot.findViewById(R.id.tv_sn);
		tv_ordertime = (TextView) foot.findViewById(R.id.tv_ordertime);
		tv_payway = (TextView) foot.findViewById(R.id.tv_payway);
		tv_phone = (TextView) foot.findViewById(R.id.tv_phone);
		tv_address = (TextView) foot.findViewById(R.id.tv_address);
		btn_pay = (Button) foot.findViewById(R.id.btn_pay);
		iv_numline = (ImageView) foot.findViewById(R.id.iv_numline);
		
		if (mOrder.get(0).getOrder_status().equals("0")) {
			if (head == null) {
				head = LayoutInflater.from(this).inflate(R.layout.layout_unfinishorderheadforpay, null, false);
				elv_list.addHeaderView(head);
				ll_cancelpay = (LinearLayout) head.findViewById(R.id.ll_cancelpay);
				ll_cancelpay.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						CancelOrder();
					}
				});
			}
			btn_pay.setVisibility(View.VISIBLE);
			iv_numline.setVisibility(View.VISIBLE);
		}else {
			btn_pay.setVisibility(View.GONE);
			iv_numline.setVisibility(View.GONE);
		}
		
	}
	
	protected void CancelOrder() {
		new NetOrderCancel(Config.getCachedToken(this), order_sn, new NetOrderCancel.SuccessCallback() {
			@Override
			public void onSuccess() {
				finish();
			}
		}, new NetOrderCancel.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				Toast.makeText(Aty_UnfinishedOrder.this, "取消订单失败，请联系客服！", Toast.LENGTH_LONG).show();
			}
		});
	}

	public void inflateView(){
		tv_sn.setText("订单号码： " + mOrder.get(0).getOrder_sn());
		tv_ordertime.setText("订单时间： " + mOrder.get(0).getShipping_time());
		if (mOrder.get(0).getPay_id().equals("1")) {
			tv_payway.setText("支付方式： 货到付款");
		}else {
			tv_payway.setText("支付方式： 在线支付");
		}
		tv_phone.setText("手机号码： " + mOrder.get(0).getRecipient_phone());
		tv_address.setText("收货地址： " + mOrder.get(0).getAddress());
		
		btn_pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Aty_UnfinishedOrder.this, Aty_Pay.class);
				intent.putExtra(Config.KEY_AMOUNT, mOrder.get(0).getOrderamount() * 100);//orderAmount.getAmount() * 100
				intent.putExtra(Config.KEY_ORDERSN, mOrder.get(0).getOrder_sn());
				startActivity(intent);
				finish();
			}
		});
		
	}

	private void getData() {
		new  NetHistoryOrderInfo(this, Config.getCachedToken(this), order_sn, new NetHistoryOrderInfo.SuccessCallback() {
			@Override
			public void onSuccess(List<API_CartShop> orders) {
				mOrder = orders;
				for(int i = 0; i < orderAdapter.getGroupCount(); i++){  
					elv_list.expandGroup(i);  
				}
				initView();
				inflateView();
			}
		}, new NetHistoryOrderInfo.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)) {
					
				}else {
					Toast.makeText(Aty_UnfinishedOrder.this, "获取数据失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public class OrderAdapter extends BaseExpandableListAdapter{
		
		GroupCell groupCell;
		ItemCell itemCell;
		
		@Override
		public int getGroupCount() {
			return mOrder.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return mOrder.get(groupPosition).getDish_list().size();
		}

		@Override
		public API_CartShop getGroup(int groupPosition) {
			return mOrder.get(groupPosition);
		}

		@Override
		public API_CartDish getChild(int groupPosition, int childPosition) {
			return mOrder.get(groupPosition).getDish_list().get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_unfinishordershop, parent, false);
				groupCell = new GroupCell();
				groupCell.tv_shopName = (TextView) convertView.findViewById(R.id.tv_shopName);
				groupCell.tv_totalnum = (TextView) convertView.findViewById(R.id.tv_totalnum);
				groupCell.tv_entershop = (TextView) convertView.findViewById(R.id.tv_entershop);
				convertView.setTag(groupCell);
			}else {
				groupCell = (GroupCell) convertView.getTag();
			}
			final API_CartShop cartShop = getGroup(groupPosition);
			groupCell.tv_shopName.setText(cartShop.getShop_name());
			groupCell.tv_totalnum.setText(cartShop.getShop_real_price() + "元");
			groupCell.tv_entershop.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Aty_UnfinishedOrder.this, Aty_dish.class);
					intent.putExtra(Config.KEY_SHOPNAME, cartShop.getShop_name());
					Config.cacheShopId(Aty_UnfinishedOrder.this, cartShop.getShop_id());
					startActivity(intent);
				}
			});
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_unfinishorderdish, parent, false);
				itemCell = new ItemCell();
				itemCell.tv_dishname = (TextView) convertView.findViewById(R.id.tv_dishname);
				itemCell.tv_dishnum = (TextView) convertView.findViewById(R.id.tv_dishnum);
				itemCell.tv_dishprice = (TextView) convertView.findViewById(R.id.tv_dishprice);
				convertView.setTag(itemCell);
			}else {
				itemCell = (ItemCell) convertView.getTag();
			}
			API_CartDish cartDish = getChild(groupPosition, childPosition);
			itemCell.tv_dishname.setText(cartDish.getDish_name());
			itemCell.tv_dishnum.setText(cartDish.getNumber() + "");
			itemCell.tv_dishprice.setText(cartDish.getDish_price() + "元");
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}
		
		public class GroupCell{
			TextView tv_shopName, tv_totalnum, tv_entershop;
		}
		
		public class ItemCell{
			TextView tv_dishname, tv_dishnum, tv_dishprice;
		}
		
	}
}
