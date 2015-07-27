package com.darna.wmxfx.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darna.wmxfx.Config;
import com.darna.wmxfx.R;
import com.darna.wmxfx.adapter.FrgDishAdapter;
import com.darna.wmxfx.adapter.FrgDishLeftAdapter;
import com.darna.wmxfx.atys.Aty_Cart;
import com.darna.wmxfx.bean.API_Cart;
import com.darna.wmxfx.bean.Dish;
import com.darna.wmxfx.bean.DishType;
import com.darna.wmxfx.bean.ShopInfo;
import com.darna.wmxfx.net.NetCartInfo;
import com.darna.wmxfx.net.NetDish;
import com.darna.wmxfx.net.NetLocate;
import com.darna.wmxfx.net.NetShopInfo;
import com.darna.wmxfx.utils.PinnedHeaderListView;

public class Frg_Dish extends Fragment {
	View view;
	ListView leftListView;
	PinnedHeaderListView rightListView;
	FrgDishAdapter frgDishAdapter;
	FrgDishLeftAdapter frgDishLeftAdapter;
	List<DishType> dishTypes = new ArrayList<DishType>();
	private boolean isScroll = false;
	RelativeLayout rl_cartbottom, rl_shopactivity;
	TextView tv_cartconfirm, tv_activity1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.frg_dish, container, false);
			frgDishAdapter = new FrgDishAdapter(getActivity(), view);
			frgDishLeftAdapter = new FrgDishLeftAdapter(getActivity());
			init();
		}
		ViewGroup parent = (ViewGroup)view.getParent();  
        if(parent != null) {  
            parent.removeView(view);  
        }
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getCartData();
		getData();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getCartData();
	} 
	
	private void getActivityDesc() {
		new NetShopInfo(getActivity(),Config.getCachedToken(getActivity()), Config.getCachedShopId(getActivity()), new NetShopInfo.SuccessCallback() {
			@Override
			public void onSuccess(ShopInfo shopInfo) {
				if (!shopInfo.getActivitydesc().equals(null) && !shopInfo.getActivitydesc().equals("") && !shopInfo.getActivitydesc().equals("null")) {
					rl_shopactivity.setVisibility(View.VISIBLE);
					tv_activity1.setText(shopInfo.getActivitydesc());
				}
			}
		}, new NetShopInfo.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				
			}
		});
	}

	private void getCartData() {
		new NetCartInfo(getActivity(), Config.getCachedToken(getActivity()), new NetCartInfo.SuccessCallback() {
			@Override
			public void onSuccess(API_Cart CartInfo) {
				frgDishAdapter.cartUtil.cartInfo = CartInfo;
				frgDishAdapter.initView();
				
			}
		}, new NetCartInfo.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("获取购物车信息失败");
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
					setAddressInSession(true);
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession(true);
				}
			}
		});
	}
	
	private void getData() {
		new NetDish(getActivity(), Config.getCachedToken(getActivity()), Config.getCachedShopId(getActivity()), new NetDish.SuccessCallback() {
			@Override
			public void onSuccess(List<Dish> dishes) {
				frgDishAdapter.addAll(dishes);
				getType(dishes);
				getActivityDesc();
			}
		}, new NetDish.FailCallback() {
			@Override
			public void onFail(String errorCode) {
				if (errorCode.equals(Config.RESULT_STATUS_FAIL)){
					System.out.println("获取商家信息失败");
					
				}else if(errorCode.equals(Config.RESULT_STATUS_INVALID_TOKEN)){
					setAddressInSession(false);
				}else if (errorCode.equals(Config.RESULT_STATUS_UNLOCATE)) {
					setAddressInSession(false);
				}
			}
		});
	}
	
	public void getType(List<Dish> dishes){
		//List<DishType> typeList = new ArrayList<DishType>();
		DishType type;
		for (int i = 0; i < dishes.size(); i++) {
			type = new DishType();
			type.setTypeName(dishes.get(i).getType());
			if (i == 0) {
				type.setTypeFlag(true);
			}else {
				type.setTypeFlag(false);
			}
			dishTypes.add(type);
		}
		frgDishLeftAdapter.add(dishTypes);
	}
	 
	private void setAddressInSession(Boolean flag) {
		Map<String, String> poiMap = Config.getCachePOI(getActivity());
		if (flag) {
			new NetLocate(Config.getCachedToken(getActivity()), poiMap.get(Config.KEY_POSTITLE), 
					poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
						@Override
						public void onSuccess() {
							getCartData();
						}
					}, new NetLocate.FailCallback() {
						@Override
						public void onFail() {
							System.out.println("上传缓存地址到服务器失败");
						}
					});
		}else {
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
	}

	private void init() {
		rightListView = (PinnedHeaderListView) view.findViewById(R.id.pinnedListView);
		rightListView.setAdapter(frgDishAdapter);
		
		View footerView = LayoutInflater.from(getActivity()).inflate(R.layout.list_footer, null, false);
		rightListView.addFooterView(footerView);
		
		/*rightListView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(getActivity(), Aty_DishIndex.class));
			}
		});*/
		
		leftListView = (ListView) view.findViewById(R.id.left_listview);
		leftListView.setAdapter(frgDishLeftAdapter);
		
		rl_shopactivity = (RelativeLayout) view.findViewById(R.id.rl_shopactivity);
		tv_activity1 = (TextView) view.findViewById(R.id.tv_activity1);
		
		leftListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
					isScroll = false;
					setLeft(position);
					
					int rightSection = 0;
					for(int i=0;i<position;i++){
						rightSection += frgDishAdapter.getCountForSection(i)+1;
					}
					rightListView.setSelection(rightSection);
			}
		});
		
		rightListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(isScroll){
					setLeft(frgDishAdapter.getSectionForPosition(firstVisibleItem));
					isScroll = false;
				}else{
					isScroll = true;
				}
			}
		});
		
		leftListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				isScroll = false;
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				isScroll = false;
			}
		});
		
		rl_cartbottom = (RelativeLayout) view.findViewById(R.id.rl_cartbottom);
		tv_cartconfirm = (TextView) view.findViewById(R.id.tv_cartconfirm);
		rl_cartbottom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callCart();
			}
		});
		
		tv_cartconfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callCart();
			}
		});
		
	}
	
	public void callCart(){
		startActivity(new Intent(getActivity(), Aty_Cart.class));
	}
	
	public void setLeft(int position){
		for (int i = 0; i < dishTypes.size(); i++) {
			if (i == position) {
				dishTypes.get(i).setTypeFlag(true);
			}else {
				dishTypes.get(i).setTypeFlag(false);
			}
		}
		frgDishLeftAdapter.notifyDataSetChanged();
		if (position <= leftListView.getFirstVisiblePosition() || position >= leftListView.getLastVisiblePosition()) {
			leftListView.setSelection(position);
		}
	}
	
	
	
}
