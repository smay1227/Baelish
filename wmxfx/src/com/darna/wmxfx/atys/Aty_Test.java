package com.darna.wmxfx.atys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.darna.wmxfx.Config;
import com.darna.wmxfx.MainActivity;
import com.darna.wmxfx.R;
import com.darna.wmxfx.net.NetLocate;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Aty_Test extends ListActivity{
	public class Aty_Location extends ListActivity implements OnGetPoiSearchResultListener{
		private PoiSearch mPoiSearch = null;
		//private SuggestionSearch mSuggestionSearch = null;
		private ListView mListView;
		private PoiListAdapter poiListAdapter;
		/**
		 * 搜索关键字输入窗口
		 */
		private AutoCompleteTextView keyWorldsView = null;
		private ArrayAdapter<String> sugAdapter = null;
		private int load_Index = 0;
		
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState); 
			SDKInitializer.initialize(getApplicationContext());
	        setContentView(R.layout.aty_location);
	        
	        // 初始化搜索模块，注册搜索事件监听
	 		mPoiSearch = PoiSearch.newInstance();
	 		mPoiSearch.setOnGetPoiSearchResultListener(this);
	 		//mSuggestionSearch = SuggestionSearch.newInstance();
	 		//mSuggestionSearch.setOnGetSuggestionResultListener(this);
	 		//下拉框提示 adapter
			keyWorldsView = (AutoCompleteTextView) findViewById(R.id.et_searchkey);
			sugAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line);
			keyWorldsView.setAdapter(sugAdapter);
			
			mListView = (ListView) findViewById(android.R.id.list);
			poiListAdapter = new PoiListAdapter();
			mListView.setAdapter(poiListAdapter);
			
			/**
			 * 当输入关键字变化时，动态更新建议列表
			 */
			keyWorldsView.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable arg0) {

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {

				}

				//在EditText改变时，请求服务器获取列表
				@Override
				public void onTextChanged(CharSequence cs, int arg1, int arg2,
						int arg3) {
					if (cs.length() <= 0) {
						return;
					}
					String city = "无锡";
					/**
					 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
					 */
					/*mSuggestionSearch
							.requestSuggestion((new SuggestionSearchOption())
									.keyword(cs.toString()).city(city));*/
				}
			 });
			
			/*findViewById(R.id.bt_searchbtn).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mPoiSearch.searchInCity((new PoiCitySearchOption())
							.city("无锡")
							.keyword(keyWorldsView.getText().toString())
							.pageNum(load_Index));
				}
			});*/
		}
		
		@Override
		public void finish() {
			startActivity(new Intent(Aty_Location.this, MainActivity.class));
			super.finish();
		}
		
		@Override
		protected void onDestroy() {
			mPoiSearch.destroy();
			//mSuggestionSearch.destroy();
			super.onDestroy();
		}
		/*@Override
		public void onGetSuggestionResult(SuggestionResult res) {
			if (res == null || res.getAllSuggestions() == null) {
				return;
			}
			sugAdapter.clear();
			for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
				if (info.key != null)
					sugAdapter.add(info.key);
			}
			sugAdapter.notifyDataSetChanged();
			
		}*/
		@Override
		public void onGetPoiDetailResult(PoiDetailResult arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onGetPoiResult(PoiResult result) {
			if (result == null
					|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
				Toast.makeText(Aty_Location.this, "未找到结果", Toast.LENGTH_LONG)
				.show();
				return;
			}
			if (result.error == SearchResult.ERRORNO.NO_ERROR) {
				poiListAdapter.clear();
				poiListAdapter.addAll(result.getAllPoi());
				return;
			}
			if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

				// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
				String strInfo = "在";
				for (CityInfo cityInfo : result.getSuggestCityList()) {
					strInfo += cityInfo.city;
					strInfo += ",";
				}
				strInfo += "找到结果";
				Toast.makeText(Aty_Location.this, strInfo, Toast.LENGTH_LONG)
				    		.show();
			}
			
		}
		
		public class PoiListAdapter extends BaseAdapter{

			@Override
			public int getCount() {
				return data.size();
			}

			@Override
			public PoiInfo getItem(int position) {
				return data.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = getLayoutInflater().inflate(R.layout.aty_baidumap_cell, parent, false);
					baiduMapCell = new BaiduMapCell();
					baiduMapCell.tv_poiname = (TextView) convertView.findViewById(R.id.tv_poiname);
					convertView.setTag(baiduMapCell);
				}else {
					baiduMapCell = (BaiduMapCell) convertView.getTag(); 
				}
				PoiInfo poiInfo = getItem(position);
				baiduMapCell.tv_poiname.setText(poiInfo.name);
				
				return convertView;
			}
			
			public void addAll(List<PoiInfo> data) {
				this.data.addAll(data);
				notifyDataSetChanged();

			}

			public void clear() {
				data.clear();
				notifyDataSetChanged();
			}
			
			public class BaiduMapCell{
				TextView tv_poiname;
			}
			
			private List<PoiInfo> data = new ArrayList<PoiInfo>();
			private BaiduMapCell baiduMapCell;
		}
		
		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			PoiInfo poiInfo = poiListAdapter.getItem(position);
			String poi = poiInfo.name;
			String street = poiInfo.address;
			String lng = String.valueOf(poiInfo.location.latitude);
			String lat = String.valueOf(poiInfo.location.longitude);
			Config.cachePOI(Aty_Location.this, poi, street, lat, lng);
			
			Map<String, String> poiMap = Config.getCachePOI(Aty_Location.this);
			new NetLocate(Config.getCachedToken(Aty_Location.this), poiMap.get(Config.KEY_POSTITLE), 
					poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
						@Override
						public void onSuccess() {
							finish();
						}
					}, new NetLocate.FailCallback() {
						@Override
						public void onFail() {
							System.out.println("上传缓存地址到服务器失败");
						}
					});
		}
	}}
