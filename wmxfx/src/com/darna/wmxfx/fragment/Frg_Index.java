package com.darna.wmxfx.fragment;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.adapter.FrgIndexAdapter;
import com.darna.wmxfx.atys.Aty_Location;
import com.darna.wmxfx.atys.Aty_dish;
import com.darna.wmxfx.bean.Shop;
import com.darna.wmxfx.dialog.ShopSequenceDialog;
import com.darna.wmxfx.dialog.ShopTasteDialog;
import com.darna.wmxfx.net.NetLocate;
import com.darna.wmxfx.net.NetShop;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class Frg_Index extends ListFragment {
	View view;
	//SlideShowView slideShowView;
	RelativeLayout sequenceLayout, tasteLayout, rl_location;
	TextView tv_sequence, tv_alltaste, tv_poi;
	ImageView iv_sequence, iv_alltaste;
	private String tastecode = Config.KEY_ALLTASTE, sequencecode = Config.KEY_ALLSEQUENCE;
	String shop_type_sub = "", sort = "0", search_key = "";
	
	FrgIndexAdapter frgIndexAdapter;
	private PullToRefreshListView mPullRefreshListView;
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			//slideShowView = new SlideShowView(getActivity());
			view = inflater.inflate(R.layout.frg_index, container, false);
			init();
			getData();
		}
		//判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。  
        ViewGroup parent = (ViewGroup)view.getParent();  
        if(parent != null) {  
            parent.removeView(view);  
        }
		return view;
	}
	
	public void getData(){
		new NetShop(getActivity(), Config.getCachedToken(getActivity()),search_key, shop_type_sub, sort, new NetShop.SuccessCallback() {
			@Override
			public void onSuccess(List<Shop> shops) {
				frgIndexAdapter.add(shops);
				mPullRefreshListView.onRefreshComplete();  
			}
		}, new NetShop.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("获取商家信息失败");
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
						getData();
					}
				}, new NetLocate.FailCallback() {
					@Override
					public void onFail() {
						System.out.println("上传缓存地址到服务器失败");
					}
				});
	}
	
	/**
	 * 初始化页面，定位，搜索,添加点击事件
	 */
	public void init(){
		//定位的点击事件
		view.findViewById(R.id.rl_location).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		sequenceLayout = (RelativeLayout) view.findViewById(R.id.rl_sequence);
		tasteLayout = (RelativeLayout) view.findViewById(R.id.rl_alltaste);
		rl_location = (RelativeLayout) view.findViewById(R.id.rl_location);
		
		tv_alltaste = (TextView) view.findViewById(R.id.tv_alltaste);
		tv_sequence = (TextView) view.findViewById(R.id.tv_sequence);
		iv_alltaste = (ImageView) view.findViewById(R.id.iv_alltaste);
		iv_sequence = (ImageView) view.findViewById(R.id.iv_sequence);
		
		tv_poi = (TextView) view.findViewById(R.id.tv_poi);
		tv_poi.setText(Config.getCachePOI(getActivity()).get(Config.KEY_POSTITLE));
		
		
		mPullRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		
		rl_location.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), Aty_Location.class));
				getActivity().finish();
			}
		});
		
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
	
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
	
				// Do work to refresh the list here.
				getData();
			}
		});
		
		//mPullRefreshListView.setRefreshing(true);
		
		tasteLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iv_alltaste.setImageResource(R.drawable.rup);
				Intent intent = new Intent(getActivity(), ShopTasteDialog.class);
				intent.putExtra(Config.KEY_TASTECODE, tastecode);
				startActivityForResult(intent, 0);
			}
		});
		
		sequenceLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iv_sequence.setImageResource(R.drawable.rup);
				Intent intent = new Intent(getActivity(), ShopSequenceDialog.class);
				intent.putExtra(Config.KEY_SEQUENCRCODE, sequencecode);
				startActivityForResult(intent, 1);
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			if (requestCode == 0) {
				tastecode = data.getStringExtra(Config.KEY_TASTECODE);
				tv_alltaste.setText(tastecode);
			}else if (requestCode == 1) {
				sequencecode = data.getStringExtra(Config.KEY_SEQUENCRCODE);
				tv_sequence.setText(sequencecode);
			}
			mPullRefreshListView.setRefreshing(true);
			regetData();
		}
		
		iv_alltaste.setImageResource(R.drawable.rdown);
		iv_sequence.setImageResource(R.drawable.rdown);
	}
	
	private void regetData() {
		if (tastecode.equals(Config.KEY_FASTFOOD)) {
			shop_type_sub = "G1";
		}else if (tastecode.equals(Config.KEY_CHINAFOOD)) {
			shop_type_sub = "G2";
		}else if (tastecode.equals(Config.KEY_WESTFOOD)) {
			shop_type_sub = "G3";
		}else if (tastecode.equals(Config.KEY_TOKYOFOOD)) {
			shop_type_sub = "G4";
		}else if (tastecode.equals(Config.KEY_FOREIGNFOOD)) {
			shop_type_sub = "G5";
		}else if (tastecode.equals(Config.KEY_DRINK)) {
			shop_type_sub = "H1";
		}else if (tastecode.equals(Config.KEY_SNACKFOOD)) {
			shop_type_sub = "H2";
		}else if (tastecode.equals(Config.KEY_CAKE)) {
			shop_type_sub = "H3";
		}else if (tastecode.equals(Config.KEY_DONUT)) {
			shop_type_sub = "H4";
		}else {
			shop_type_sub = "";
		}
		
		if (sequencecode.equals(Config.KEY_ALLSEQUENCE)) {
			sort = "0";
		}else if (sequencecode.equals(Config.KEY_LOWDELIVER)) {
			sort = "2";
		}
		getData();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Shop shop = frgIndexAdapter.getItem(position - 1);
		Config.cacheShopId(getActivity(), shop.getShop_id());
		Intent intent = new Intent(getActivity(), Aty_dish.class);
		intent.putExtra(Config.KEY_SHOPNAME, shop.getShop_name());
		//intent.putExtra(Config.KEY_SHOPID, shop.getShop_id());
		startActivity(intent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		frgIndexAdapter = new FrgIndexAdapter(getActivity());
		setListAdapter(frgIndexAdapter);
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
}
