package com.darna.wmxfx.fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.atys.Aty_UnfinishedOrder;
import com.darna.wmxfx.bean.API_Order;
import com.darna.wmxfx.net.NetHistoryOrder;
import com.darna.wmxfx.net.NetLocate;
import com.darna.wmxfx.net.NetLogin;
import com.ta.utdid2.android.utils.StringUtils;
import com.tencent.mm.sdk.modelbase.BaseResp.ErrCode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Frg_HistoryOrder extends Fragment {
	
	ListView mListView;
	TextView tv_login, tv_datanull;
	List<API_Order> mOrders = new ArrayList<API_Order>();
	HistoryOrderAdapter historyOrderAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.frg_historyorder, container, false);
		
		mListView = (ListView) view.findViewById(R.id.mlist);
		tv_login = (TextView) view.findViewById(R.id.tv_login);
		tv_datanull = (TextView) view.findViewById(R.id.tv_datanull);
		
		historyOrderAdapter = new HistoryOrderAdapter();
		mListView.setAdapter(historyOrderAdapter);
		
		getData();
		
		return view;
	}
	
	
	private void getData() {
		new NetHistoryOrder(getActivity(), Config.getCachedToken(getActivity()), "3", new NetHistoryOrder.SuccessCallback() {
			
			@Override
			public void onSuccess(List<API_Order> orders) {
				tv_login.setVisibility(View.GONE);
				mOrders = orders;
				historyOrderAdapter.notifyDataSetChanged();
			}
		}, new NetHistoryOrder.FailCallback() {
			
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)) {
					addressSet();
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOGIN)) {
					login();
				}else if (errorCode.equals(Config.RESULT_DATA_NULL)) {
					tv_datanull.setVisibility(View.VISIBLE);
				}else {
					Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	public void login(){
		Map<String, String> loginMap = Config.getcacheLogin(getActivity());
		if (loginMap.get(Config.KEY_MOBILE_PHONE) == null) {
			//显示未登陆页面
			tv_login.setVisibility(View.VISIBLE);
		}else {
			//后台登陆
			new NetLogin(getActivity(), Config.getCachedToken(getActivity()), loginMap.get(Config.KEY_MOBILE_PHONE), loginMap.get(Config.KEY_PASSWORD), new NetLogin.SuccessCallback() {
				@Override
				public void onSuccess() {
					getData();
				}
			}, new NetLogin.FailCallback() {
				@Override
				public void onFail(String errorCode) {
					if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
						System.out.println("登陆失败");
					}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
						login();
					}else if (errorCode.equals(Config.RESULT_STATUS_UNLOGIN)) {
						login();
					}
				}
			});
		}
	}
	
	public void addressSet(){
		Map<String, String> poiMap = Config.getCachePOI(getActivity());
		if (poiMap.get(Config.KEY_POSTITLE) == null) {//本地没有地址缓存
			
		}else {//本地有地址缓存，重传服务器，设置服务器的缓存
			new NetLocate(Config.getCachedToken(getActivity()), poiMap.get(Config.KEY_POSTITLE), 
					poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
						@Override
						public void onSuccess() {
							login();
						}
					}, new NetLocate.FailCallback() {
						@Override
						public void onFail() {
							Toast.makeText(getActivity(), "先传地址后登陆失败", Toast.LENGTH_SHORT).show();
						}
					});
		}
	}

	public class HistoryOrderAdapter extends BaseAdapter{

		OrderCell orderCell;
		
		@Override
		public int getCount() {
			return mOrders.size();
		}

		@Override
		public API_Order getItem(int position) {
			return mOrders.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.item_orderitem, parent, false);
				orderCell = new OrderCell();
				orderCell.tv_ordersn = (TextView) convertView.findViewById(R.id.tv_ordersn);
				orderCell.tv_shopname = (TextView) convertView.findViewById(R.id.tv_shopname);
				orderCell.tv_orderamount = (TextView) convertView.findViewById(R.id.tv_orderamount);
				orderCell.ll_orderitem = (LinearLayout) convertView.findViewById(R.id.ll_orderitem);
				convertView.setTag(orderCell);
			}else {
				orderCell = (OrderCell) convertView.getTag();
			}
			final API_Order order = getItem(position);
			orderCell.tv_ordersn.setText("订单号码：" + order.getOrder_sn() + "   " + order.getAdd_time());
			Set<String> set = new HashSet<String>();
			for (int i = 0; i < order.getShops().size(); i++) {
				set.add(order.getShops().get(i).getShop_name());
			}
			String shopName = org.apache.commons.lang.StringUtils.join(set, "/");
			orderCell.tv_shopname.setText(shopName);
			orderCell.tv_orderamount.setText("￥" + order.getOrder_amount());
			
			orderCell.ll_orderitem.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), Aty_UnfinishedOrder.class);
					intent.putExtra(Config.KEY_ORDERSN, order.getOrder_sn());
					startActivity(intent);
				}
			});
			
			return convertView;
		}
		
		public class OrderCell{
			TextView tv_ordersn, tv_shopname, tv_orderamount;
			LinearLayout ll_orderitem;
		}
		
	}
	
}
