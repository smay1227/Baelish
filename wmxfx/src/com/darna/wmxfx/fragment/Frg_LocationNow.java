package com.darna.wmxfx.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.darna.wmxfx.Config;
import com.darna.wmxfx.MainActivity;
import com.darna.wmxfx.R;
import com.darna.wmxfx.atys.Aty_Test.Aty_Location;
import com.darna.wmxfx.atys.Aty_Test.Aty_Location.PoiListAdapter;
import com.darna.wmxfx.net.NetLocate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Frg_LocationNow extends Fragment implements OnGetPoiSearchResultListener{
	ListView mListView;
	private PoiSearch mPoiSearch = null;
	EditText et_searchkey;
	private int load_Index = 0;
	private PoiListAdapter poiListAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frg_locationnow, container, false);
		
		// 初始化搜索模块，注册搜索事件监听
 		mPoiSearch = PoiSearch.newInstance();
 		mPoiSearch.setOnGetPoiSearchResultListener(this);
		
		mListView = (ListView) view.findViewById(android.R.id.list);
		poiListAdapter = new PoiListAdapter();
		mListView.setAdapter(poiListAdapter);
		
		et_searchkey = (EditText) getActivity().findViewById(R.id.et_searchkey);
		
		
		mPoiSearch.searchInCity((new PoiCitySearchOption())
				.city("无锡")
				.keyword(et_searchkey.getText().toString())
				.pageNum(load_Index));
		
		return view;
	}
	
	@Override
	public void onDestroy() {
		mPoiSearch.destroy();
		//mSuggestionSearch.destroy();
		super.onDestroy();
	}
	
	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		
	}
	
	@Override
	public void onGetPoiResult(PoiResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(getActivity(), "未找到结果", Toast.LENGTH_LONG)
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
			Toast.makeText(getActivity(), strInfo, Toast.LENGTH_LONG)
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
				convertView = getActivity().getLayoutInflater().inflate(R.layout.aty_baidumap_cell, parent, false);
				baiduMapCell = new BaiduMapCell();
				baiduMapCell.tv_poiname = (TextView) convertView.findViewById(R.id.tv_poiname);
				baiduMapCell.ll_mapcell = (LinearLayout) convertView.findViewById(R.id.ll_mapcell);
				convertView.setTag(baiduMapCell);
			}else {
				baiduMapCell = (BaiduMapCell) convertView.getTag(); 
			}
			final PoiInfo poiInfo = getItem(position);
			baiduMapCell.tv_poiname.setText(poiInfo.name);
			baiduMapCell.ll_mapcell.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String poi = poiInfo.name;
					String street = poiInfo.address;
					String lng = String.valueOf(poiInfo.location.latitude);
					String lat = String.valueOf(poiInfo.location.longitude);
					Config.cachePOI(getActivity(), poi, street, lat, lng);
					
					Map<String, String> poiMap = Config.getCachePOI(getActivity());
					new NetLocate(Config.getCachedToken(getActivity()), poiMap.get(Config.KEY_POSTITLE), 
							poiMap.get(Config.KEY_POSADDRESS), poiMap.get(Config.KEY_POSX), poiMap.get(Config.KEY_POSY), new NetLocate.SuccessCallback() {
								@Override
								public void onSuccess() {
									getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
									getActivity().finish();
								}
							}, new NetLocate.FailCallback() {
								@Override
								public void onFail() {
									System.out.println("上传缓存地址到服务器失败");
								}
							});
				}
			});
			
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
			LinearLayout ll_mapcell;
		}
		
		private List<PoiInfo> data = new ArrayList<PoiInfo>();
		private BaiduMapCell baiduMapCell;
	}
	
}
